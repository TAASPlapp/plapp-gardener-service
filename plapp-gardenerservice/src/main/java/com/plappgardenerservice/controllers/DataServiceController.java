package com.plappgardenerservice.controllers;

import com.plappgardenerservice.entities.Schedule;
import com.plappgardenerservice.services.ScheduleService;
import org.springframework.web.bind.annotation.*;
import java.util.Date;

@RestController
public class DataServiceController {

    /* This method is invoked whenever a user requests
     * the insertion of a new schedule (e.g. water, prune, ...)
     */
    @PutMapping("/add_schedule")
    Schedule addSchedule(@RequestParam long plantID, @RequestParam Date date, @RequestParam String action) {
        System.out.println("INVOKED");
        System.out.println(plantID);
        System.out.println(action);
        System.out.println(date);
        ScheduleService scheduleService = new ScheduleService();
        Schedule newSchedule = scheduleService.createSchedule(plantID, date, action);

        return newSchedule;
    }

}
