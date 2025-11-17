package edu.dosw.rideci.infrastructure.persistance.Repository;

import org.springframework.stereotype.Component;

import edu.dosw.rideci.application.port.out.EventPublisher;
import lombok.RequiredArgsConstructor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

@Component
@RequiredArgsConstructor
public class RabbitEventPublisher implements EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publish(Object event, String routingKey) {
        rabbitTemplate.convertAndSend("travel.exchange", routingKey, event);
    }

}
