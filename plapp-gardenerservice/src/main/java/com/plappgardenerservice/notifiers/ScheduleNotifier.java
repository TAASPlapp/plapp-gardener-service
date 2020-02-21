package com.plappgardenerservice.notifiers;

import com.plappgardenerservice.entities.ScheduleAction;
import com.plappgardenerservice.services.RabbitMQSender;
import com.plappgardenerservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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

    @Scheduled(fixedRate = 5000)
    public void checkForSchedules() {
        System.out.println("Schedule Notifier online");
        List<ScheduleAction> scheduleActions = scheduleService.findAll();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date d = new Date();
        System.out.println(dateFormat.format(d));

        for(ScheduleAction sa : scheduleActions){

            System.out.println("Schedule date: " + dateFormat.format(sa.getDate()));
        }
        /*
        Date currentDate = new Date();
        System.out.println(currentDate.getTime());
        List<Schedule> schedules = scheduleService.findAll();
        for(Schedule s : schedules){

        }
         */

        /*
        ScheduleAction toSend = new ScheduleAction(99,new Date(),"water",2);
        rabbitMQSender.send(toSend);

         */
    }
}