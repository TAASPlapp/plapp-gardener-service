package com.plapp.gardenerservice.services;

import com.plapp.entities.schedules.Diagnosis;
import com.plapp.entities.schedules.ScheduleAction;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitMQSender {

    @Autowired
    private AmqpTemplate rabbitTemplate;

    @Value("${javainuse.rabbitmq.exchange}")
    private String exchange;

    @Value("${javainuse.rabbitmq.routingkey}")
    private String routingkey;

    public void sendScheduleAction(ScheduleAction scheduleAction) {
        rabbitTemplate.convertAndSend(exchange, routingkey, scheduleAction);
        System.out.println("Send msg = " + scheduleAction);
    }

    public void sendDiagnosis(Diagnosis diagnosis){
        rabbitTemplate.convertAndSend(exchange, routingkey, diagnosis);
        System.out.println("Send msg = " + diagnosis);
    }
}
