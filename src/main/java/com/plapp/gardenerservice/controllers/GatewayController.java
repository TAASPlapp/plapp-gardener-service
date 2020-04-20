package com.plapp.gardenerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.schedules.ScheduleAction;
import com.plapp.gardenerservice.services.DiagnosisService;
import com.plapp.gardenerservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
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

    @GetMapping(value = "gardener/{plantId}/diagnose")
    public String getPlantDiagnosis(String plantImageURL, String plantId) throws InterruptedException, IOException {
        Mono<String> result = WebClient.create()
                .get()
                .uri(uriBuilder -> {
                    try {
                        return uriBuilder.scheme("https")
                            .host("plapp-diagnosis-service.herokuapp.com")
                            .path("diagnose")
                            .queryParam("url", URLEncoder.encode(plantImageURL, "UTF-8"))
                            .build();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return null;
                    }
                })
                .retrieve()
                .bodyToMono(String.class);

        result.subscribe(diagnosis -> {
            try {
                System.out.println(diagnosis);
                Diagnosis plantDiagnosis = objectMapper.readValue(diagnosis, Diagnosis.class);
                plantDiagnosis.setPlantId(plantId);
                diagnosisService.createDiagnosis(plantDiagnosis);
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
