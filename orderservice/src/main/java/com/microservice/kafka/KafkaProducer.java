package com.microservice.kafka;

import com.microservice.dto.OrderEventDto;

public interface KafkaProducer {
    void sendOrderEvent(OrderEventDto event);
}
