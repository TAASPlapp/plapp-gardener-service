package com.plappgardenerservice.repositories;

import com.plappgardenerservice.entities.Diagnosis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Integer> {
    public Diagnosis findDiagnosisByPlantID(String plantID);
}
