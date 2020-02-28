package com.plappgardenerservice.services;

import com.plapp.entities.schedules.ScheduleAction;
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

    public ScheduleAction createSchedule(long userId, long plantId, Date date, String action, int periodicity, String additionalInfo) {
        ScheduleAction toSave = new ScheduleAction(userId, plantId, date, action, periodicity, additionalInfo);
        return scheduleRepository.save(toSave);
    }

    public void deleteSchedule(ScheduleAction toDelete){
        scheduleRepository.delete(toDelete);
    }



}
