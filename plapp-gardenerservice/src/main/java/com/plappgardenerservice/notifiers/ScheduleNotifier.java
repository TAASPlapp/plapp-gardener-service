package com.plappgardenerservice.notifiers;

import com.plappgardenerservice.entities.ScheduleAction;
import com.plappgardenerservice.services.RabbitMQSender;
import com.plappgardenerservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.time.*;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/* This class implements a thread checking for schedules action to be notified to the user */
@Component
public class ScheduleNotifier {

    @Autowired
    ScheduleService scheduleService;
    @Autowired
    RabbitMQSender rabbitMQSender;
    Clock clock;
    long timeBeforeNotification; //in ms

    @PostConstruct
    public void init() {
        clock = Clock.systemUTC();
        timeBeforeNotification = 3600000; //one hour
    }

    @Scheduled(fixedRate = 5000)
    public void checkForSchedules() throws ParseException {
        System.out.println("Schedule Notifier online");
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
                //rabbitMQSender.send(sa);
                scheduleService.deleteSchedule(sa);
            }
        }

        /*
        ScheduleAction toSend = new ScheduleAction(99,new Date(),"water",2);
        rabbitMQSender.send(toSend);

         */
    }
}