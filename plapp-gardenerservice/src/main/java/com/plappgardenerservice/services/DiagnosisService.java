package com.plappgardenerservice.services;

import com.plappgardenerservice.entities.Diagnosis;
import com.plappgardenerservice.entities.ScheduleAction;
import com.plappgardenerservice.repositories.DiagnosisRepository;
import com.plappgardenerservice.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
public class DiagnosisService {

        @Autowired
        private DiagnosisRepository diagnosisRepository;

        /*
        @Autowired
        public DiagnosisService(DiagnosisRepository diagnosisRepository) {
            this.diagnosisRepository = diagnosisRepository;
        }

         */

        @Transactional
        @PostConstruct
        public void init() {
        }

        public List<Diagnosis> findAll() {
            return diagnosisRepository.findAll();
        }

        public Diagnosis createDiagnosis(Diagnosis toAdd) {
            return diagnosisRepository.save(toAdd);
        }

        public void deleteDiagnosis(Diagnosis toDelete){
            diagnosisRepository.delete(toDelete);
        }

        public Diagnosis findDiagnosisByPlantID(String plantID){
            return diagnosisRepository.findDiagnosisByPlantID(plantID);
        }


    }
