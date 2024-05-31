package com.microservice.kafka;


import com.microservice.dto.OrderEventDto;

public interface KafkaConsumer {
    void handleOrderEvent(OrderEventDto orderEvent);
}
