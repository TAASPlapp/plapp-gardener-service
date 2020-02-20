package com.plappgardenerservice.services;

import com.plappgardenerservice.entities.Schedule;
import com.plappgardenerservice.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;


import java.util.Date;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Transactional
    @PostConstruct
    public void init() {
    }

    public List<Schedule> findAll() {
        return scheduleRepository.findAll();
    }

    public Schedule createSchedule(long plantID, Date date, String action, int periodicity) {
        return scheduleRepository.save(new Schedule(plantID, date, action, periodicity));
    }



}
