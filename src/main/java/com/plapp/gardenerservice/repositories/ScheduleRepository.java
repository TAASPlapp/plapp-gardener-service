package com.plapp.gardenerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.plapp.entities.schedules.ScheduleAction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface ScheduleRepository extends JpaRepository<ScheduleAction, Long> {
    List<ScheduleAction> findAllByPlantId(long plantId);
    void deleteByPlantId(long plantId);
}

