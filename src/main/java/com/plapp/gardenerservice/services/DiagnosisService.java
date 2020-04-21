package com.plapp.gardenerservice.services;

import com.plapp.entities.schedules.Diagnosis;
import com.plapp.gardenerservice.repositories.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;

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
