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
    ScheduleAction addScheduleAction(@RequestBody ScheduleAction scheduleAction) {
        ScheduleAction newScheduleAction = scheduleService.createSchedule(scheduleAction);
        return newScheduleAction;
    }

    @PostMapping("/diagnose")
    public Diagnosis getPlantDiagnosis(@RequestParam String plantImageURL) throws UnsupportedEncodingException {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(
                "https://plapp-diagnosis-service.herokuapp.com/diagnose?plantImageURL=" + plantImageURL,
                Diagnosis.class
        );
    }

    @PostMapping("/{plantId}/diagnose-async")
    public void getPlantDiagnosisAsync(@PathVariable  String plantId, @RequestBody String plantImageURL) throws InterruptedException, IOException {
        Mono<Diagnosis> result = WebClient.create()
                .get()
                .uri(uriBuilder -> uriBuilder.scheme("https")
                    .host("plapp-diagnosis-service.herokuapp.com")
                    .path("diagnose")
                    .queryParam("plantImageURL", plantImageURL)
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
    void removeScheduleAction(long plantId){
        scheduleService.deleteSchedule(plantId);
    }

    @GetMapping("/{plantId}/schedule/getAll")
    List<ScheduleAction> getSchedule(long plantId){
        return scheduleService.findAllByPlantId(plantId);
    }

    @GetMapping("/actions")
    List<String> getActions(){
        return possibleActions;
    }

}
