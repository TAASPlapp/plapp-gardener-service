package com.plappgardenerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.schedules.ScheduleAction;
import com.plappgardenerservice.services.DiagnosisService;
import com.plappgardenerservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@RestController
public class GatewayController {

    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private DiagnosisService diagnosisService;

    private ObjectMapper objectMapper;
    private final List<String> possibleActions = new ArrayList<>(Arrays.asList(new String[]{"Potatura","Innaffiatura","Concimazione"}));

    @PostConstruct
    public void init(){
        objectMapper = new ObjectMapper();
    }

    /* This method is invoked whenever a user requests
     * the insertion of a new schedule (e.g. water, prune, ...)
     */
    @PutMapping("gardener/{plantId}/schedule/add")
    ScheduleAction addScheduleAction(@RequestBody ScheduleAction scheduleAction) {
        ScheduleAction newScheduleAction = scheduleService.createSchedule(scheduleAction);
        return newScheduleAction;
    }

    public String getNNUri(String plantImageURL){
        return "https://plant-info-api.herokuapp.com/cnn?="+plantImageURL;
    }

    @GetMapping(value = "gardener/{plantId}/diagnose")
    public String getPlantDiagnosis(String plantImageURL, String plantId) throws InterruptedException, IOException {
        Mono<String> result = WebClient.create()
                .get()
                .uri(getNNUri(plantImageURL))
                .retrieve()
                .bodyToMono(String.class)
                ;
        result.subscribe(diagnosis -> {
            try {
                System.out.println(diagnosis);
                diagnosisService.createDiagnosis(objectMapper.readValue(diagnosis, Diagnosis.class));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }); //diagnosis should be string (JSON)
        return "Diagnosis is processing. You will receive a notification with the results ASAP.";
    }

    @GetMapping("gardener/{plantId}/schedule/remove")
    void removeScheduleAction(long plantId){
        scheduleService.deleteSchedule(plantId);
    }

    @GetMapping("gardener/{plantId}/schedule/getAll")
    List<ScheduleAction> getSchedule(long plantId){
        return scheduleService.findAllByPlantId(plantId);
    }

    @GetMapping(value = "gardener/actions")
    List<String> getActions(){
        return possibleActions;
    }

}
