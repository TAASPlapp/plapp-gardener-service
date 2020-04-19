package com.plappgardenerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.plapp.entities.schedules.ScheduleAction;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleAction, Long> {
    public List<ScheduleAction> findAllByPlantId(long plantId);
    public void deleteByPlantId(long plantId);
}

