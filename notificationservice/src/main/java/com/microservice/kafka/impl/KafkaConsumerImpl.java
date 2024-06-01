package com.microservice.kafka.impl;


import com.microservice.dto.OrderEventDto;
import com.microservice.entity.Notification;
import com.microservice.kafka.KafkaConsumer;
import com.microservice.repository.NotificationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
public class KafkaConsumerImpl implements KafkaConsumer {

    @Autowired
    private NotificationRepository notificationRepository;

    @KafkaListener(topics = "order-notification", groupId = "notification")
    public void handleOrderEvent(OrderEventDto event) {
        log.info(String.format(
            "The order was %s with id: %s",
            event.orderEvent(), event.orderId())
        );

        Notification notification = new Notification();
        notification.setOrderId(event.orderId());
        notification.setMessage(event.orderEvent().toString());
        notification.setTimestamp(LocalDateTime.now());

        log.info("Save notification");
        notificationRepository.save(notification);
    }
}
