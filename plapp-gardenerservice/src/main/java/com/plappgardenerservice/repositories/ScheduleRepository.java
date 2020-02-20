package com.plappgardenerservice.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.plappgardenerservice.entities.Schedule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

}
