package edu.dosw.rideci.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Bean
    public Queue travelCreatedQueue() {
        return new Queue("travel.created.queue", true);
    }

    @Bean
    public Queue travelCompletedQueue() {
        return new Queue("travel.completed.queue", true);
    }

    @Bean
    public Queue travelUpdatedQueue() {
        return new Queue("travel.updated.queue", true);
    }

    @Bean
    public Queue travelCancelledEvent() {
        return new Queue("travel.cancelled.queue", true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public TopicExchange travelExchange() {
        return new TopicExchange("travel.exchange", true, false);
    }

    @Bean
    public Binding bindingTravelCancelled() {
        return BindingBuilder.bind(travelCancelledEvent()).to(travelExchange()).with("travel.cancelled");
    }

    @Bean
    public Binding bindingTravelCreated() {
        return BindingBuilder.bind(travelCreatedQueue()).to(travelExchange()).with("travel.created");
    }

    @Bean
    public Binding bindingTravelCompleted() {
        return BindingBuilder.bind(travelCompletedQueue()).to(travelExchange()).with("travel.completed");
    }

    @Bean
    public Binding bindingTravelUpdated() {
        return BindingBuilder.bind(travelUpdatedQueue()).to(travelExchange()).with("travel.updated");
    }

    // Microservicio de Perfiles -> Consumer

}