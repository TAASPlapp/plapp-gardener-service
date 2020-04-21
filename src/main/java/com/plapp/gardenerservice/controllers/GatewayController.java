package com.plapp.gardenerservice.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.schedules.ScheduleAction;
import com.plapp.gardenerservice.services.DiagnosisService;
import com.plapp.gardenerservice.services.RabbitMQSender;
import com.plapp.gardenerservice.services.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
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
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/gardener")
public class GatewayController {

    private final ScheduleService scheduleService;
    private final DiagnosisService diagnosisService;
    private final RabbitMQSender rabbitMQSender;

    private ObjectMapper objectMapper = new ObjectMapper();
    private final List<String> possibleActions = new ArrayList<>(Arrays.asList(new String[]{"Potatura","Innaffiatura","Concimazione"}));


    /* This method is invoked whenever a user requests
     * the insertion of a new schedule (e.g. water, prune, ...)
     */
    @PutMapping("/{plantId}/schedule/add")
    ScheduleAction addScheduleAction(@PathVariable Long plantId, @RequestBody ScheduleAction scheduleAction) {
        ScheduleAction newScheduleAction = scheduleService.createSchedule(scheduleAction);
        return newScheduleAction;
    }

    @PostMapping("/diagnose")
    public Diagnosis getPlantDiagnosis(@RequestBody Map<String, String> params) throws UnsupportedEncodingException {
        String plantImageURL = params.get("plantImageURL");
        System.out.println("plantImageURL: " + plantImageURL);

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                "https://plapp-diagnosis-service.herokuapp.com/diagnose?plantImageURL=" + plantImageURL,
                Diagnosis.class
        );
    }

    @PostMapping("/{plantId}/diagnose-async")
    public void getPlantDiagnosisAsync(@PathVariable String plantId, @RequestBody Map<String, String> params) throws InterruptedException, IOException {
        Mono<Diagnosis> result = WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder.scheme("https")
                    .host("plapp-diagnosis-service.herokuapp.com")
                    .path("diagnose")
                    .queryParam("plantImageURL", params.get("plantImageURL"))
                    .build())
                .retrieve()
                .bodyToMono(Diagnosis.class);

        result.subscribe(diagnosis -> {
            System.out.println(diagnosis);
            diagnosis.setPlantId(plantId);
            diagnosisService.createDiagnosis(diagnosis);
            rabbitMQSender.sendDiagnosis(diagnosis);
        });
    }

    @GetMapping("/{plantId}/schedule/remove")
    void removeScheduleAction(@PathVariable Long plantId){
        scheduleService.deleteSchedule(plantId);
    }

    @GetMapping("/{plantId}/schedule/getAll")
    List<ScheduleAction> getSchedule(@PathVariable Long plantId){
        return scheduleService.findAllByPlantId(plantId);
    }

    @GetMapping("/actions")
    List<String> getActions(){
        return possibleActions;
    }

}
