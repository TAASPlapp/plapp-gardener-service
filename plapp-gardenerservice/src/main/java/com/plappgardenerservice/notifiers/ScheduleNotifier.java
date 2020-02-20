package com.plappgardenerservice.notifiers;

import com.plappgardenerservice.entities.Schedule;
import com.plappgardenerservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class ScheduleNotifier {

    @Autowired
    ScheduleService scheduleService;

    @Scheduled(fixedRate = 5000)
    public void checkForSchedules() {
        System.out.println("Schedule Notifier online");
        Date currentDate = new Date();
        System.out.println(currentDate.getTime());
        List<Schedule> schedules = scheduleService.findAll();
        for(Schedule s : schedules){

        }
    }
}