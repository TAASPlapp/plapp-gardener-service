package com.plapp.gardenerservice.services;

import com.plapp.entities.schedules.Diagnosis;
import com.plapp.gardenerservice.repositories.DiagnosisRepository;
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

    }
