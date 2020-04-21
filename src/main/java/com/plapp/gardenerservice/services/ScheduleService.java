package com.plapp.gardenerservice.services;

import com.plapp.entities.schedules.ScheduleAction;
import com.plapp.gardenerservice.repositories.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;


import java.util.List;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;

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