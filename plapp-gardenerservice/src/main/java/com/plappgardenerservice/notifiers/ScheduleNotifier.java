package com.plappgardenerservice.notifiers;

import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.schedules.ScheduleAction;
import com.plappgardenerservice.services.DiagnosisService;
import com.plappgardenerservice.services.RabbitMQSender;
import com.plappgardenerservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.time.*;
import java.util.Date;
import java.util.List;

/*
 *  This class implements a thread checking for schedules action to be notified to the user
 */
@Component
public class ScheduleNotifier {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    DiagnosisService diagnosisService;
    @Autowired
    RabbitMQSender rabbitMQSender;
    Clock clock;
    long timeBeforeNotification; //in ms

    @PostConstruct
    public void init() {
        clock = Clock.systemUTC();
        timeBeforeNotification = 3600000; //one hour
    }

    /*
     *
     */
    public void sendDiagnosesToNotificationService(){
        List<Diagnosis> diagnoses = diagnosisService.findAll();
        for(Diagnosis d : diagnoses){
            rabbitMQSender.sendDiagnosis(d);
            diagnosisService.deleteDiagnosis(d);
        }
    }

    public void sendScheduleActionsToNotificationService(){
        List<ScheduleAction> scheduleActions = scheduleService.findAll();
        LocalDateTime currentTime = LocalDateTime.now(clock);
        System.out.println("Current time: " + currentTime);
        long currentMs = currentTime.atZone(clock.getZone()).toInstant().toEpochMilli();
        System.out.println("Current instant: " + currentMs);
        for(ScheduleAction sa : scheduleActions){
            LocalDateTime scheduleActionDate = LocalDateTime.ofInstant(sa.getDate().toInstant(),clock.getZone());
            long scheduleActionMs = scheduleActionDate.atZone(clock.getZone()).toInstant().toEpochMilli();
            System.out.println("Schedule date: " + scheduleActionDate);
            System.out.println("Schedule instant: " + scheduleActionMs);
            if(scheduleActionMs - currentMs < timeBeforeNotification){
                if(sa.getPeriodicity() > 0){
                    LocalDateTime updatedScheduleActionDate = scheduleActionDate.plusDays(sa.getPeriodicity());
                    Date date = Date.from(updatedScheduleActionDate.atZone(ZoneId.systemDefault()).toInstant());
                    ScheduleAction updatedScheduleAction = new ScheduleAction(sa.getUserId(),sa.getPlantId(),date,sa.getAction(),sa.getPeriodicity(),sa.getAdditionalInfo());
                    scheduleService.createSchedule(updatedScheduleAction);
                }
                rabbitMQSender.sendScheduleAction(sa);
                scheduleService.deleteSchedule(sa);
            }
        }
    }

    @Scheduled(fixedRate = 5000)
    public void checkForSchedules() throws ParseException {
        System.out.println("Schedule Notifier online");
        sendDiagnosesToNotificationService();
        sendScheduleActionsToNotificationService();
    }
}