package com.plappgardenerservice.services;

import com.plappgardenerservice.entities.ScheduleAction;
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

    public List<ScheduleAction> findAll() {
        return scheduleRepository.findAll();
    }

    public ScheduleAction createSchedule(long plantID, Date date, String action, int periodicity) {
        return scheduleRepository.save(new ScheduleAction(plantID, date, action, periodicity));
    }

    public void deleteSchedule(ScheduleAction toDelete){
        scheduleRepository.delete(toDelete);
    }



}
