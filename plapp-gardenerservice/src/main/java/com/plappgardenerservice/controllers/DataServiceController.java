package com.plappgardenerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plappgardenerservice.entities.Diagnosis;
import com.plappgardenerservice.entities.ScheduleAction;
import com.plappgardenerservice.services.DiagnosisService;
import com.plappgardenerservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.HashSet;

@RestController
public class DataServiceController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private DiagnosisService diagnosisService;

    private ObjectMapper objectMapper;
    private HashSet<String> imageURLs;

    @PostConstruct
    public void init(){
        objectMapper = new ObjectMapper();
        imageURLs = new HashSet<>();
    }

    /* This method is invoked whenever a user requests
     * the insertion of a new schedule (e.g. water, prune, ...)
     */
    @PutMapping("/add_schedule")
    boolean addSchedule(@RequestParam long plantID, @RequestParam Date date, @RequestParam String action, @RequestParam int periodicity) {
        /*
        System.out.println("INVOKED");
        System.out.println(plantID);
        System.out.println(action);
        System.out.println(date);
        System.out.println(periodicity);
         */
        ScheduleAction newScheduleAction = scheduleService.createSchedule(plantID, date, action, periodicity);
        return true;
    }

    public String getNNUri(String plantImageURL){
        return "https://plant-info-api.herokuapp.com/cnn?="+plantImageURL;
    }

    public void print(String str)
    {
        System.out.println(str);
    }

    @GetMapping(value = "/diagnose_image")
    public String getPlantDiagnosis(String plantImageURL, String plantID) throws InterruptedException, IOException {
        System.out.println("Starting NON-BLOCKING Controller!");
        //if there's already a diagnosis request for a given plant
        if(imageURLs.contains(plantID)){
            return "You have already sent a diagnosis request for this plant!";
        }
        imageURLs.add(plantID);
        Mono<String> result = WebClient.create()
                .get()
                .uri(getNNUri(plantImageURL))
                .retrieve()
                .bodyToMono(String.class)
                ;
        /*
        String json = "{\"plantID\":\"1213\",\"ill\":true,\"disease\":\"enterococcus plantalis\"}";
        Diagnosis d = objectMapper.readValue(json, Diagnosis.class);
        System.out.println(d);
         */
        result.subscribe(diagnosis -> {
            try {
                System.out.println(diagnosis);
                diagnosisService.createDiagnosis(objectMapper.readValue(diagnosis, Diagnosis.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }); //diagnosis should be string (JSON)

        System.out.println("INVOKED!!!!");
        System.out.println(result);
        return "Diagnosis is processing. You will receive a notification with the results ASAP.";
    }

}
