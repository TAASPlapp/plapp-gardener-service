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

    public ScheduleAction createSchedule(ScheduleAction scheduleAction) {
        return scheduleRepository.save(scheduleAction);
    }

    public void deleteSchedule(long plantId){
        scheduleRepository.deleteByPlantId(plantId);
    }

    public List<ScheduleAction> findAllByPlantId(long plantId){
        return scheduleRepository.findAllByPlantId(plantId);
    }

}