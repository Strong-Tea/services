package com.microservice.kafka.impl;

import com.microservice.kafka.KafkaProducer;
import com.microservice.dto.OrderEventDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
public class KafkaProducerImpl implements KafkaProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    private final String orderEventTopic = "order-notification";


    @Override
    public void sendOrderEvent(OrderEventDto event) {
        CompletableFuture<SendResult<String, Object>> future =
            kafkaTemplate.send(orderEventTopic, event);
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info(String.format(
                    "Successfully send event about %s order with id: %s",
                    event.orderEvent(), event.orderId())
                );
            } else {
                log.error(String.format(
                    "Cannot send event about %s order with id: %s",
                    event.orderEvent(), event.orderId())
                );
            }
        });
    }
}
