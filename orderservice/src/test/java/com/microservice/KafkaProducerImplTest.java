package com.microservice;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import com.microservice.dto.OrderEventDto;
import com.microservice.enums.OrderEvent;
import com.microservice.kafka.impl.KafkaProducerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.core.KafkaTemplate;


@ExtendWith(MockitoExtension.class)
public class KafkaProducerImplTest {

    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @InjectMocks
    private KafkaProducerImpl kafkaProducer;

    private OrderEventDto event;
    private final String orderEventTopic = "order-notification";

    @BeforeEach
    void setUp() {
        event = new OrderEventDto(UUID.randomUUID(), OrderEvent.CREATED);
    }

    @Test
    void testSendOrderEvent_Success() {
        CompletableFuture<SendResult<String, Object>> future = new CompletableFuture<>();
        future.complete(mock(SendResult.class));

        when(kafkaTemplate.send(eq(orderEventTopic), eq(event))).thenReturn(future);

        kafkaProducer.sendOrderEvent(event);

        verify(kafkaTemplate).send(eq(orderEventTopic), eq(event));
    }

    @Test
    void testSendOrderEvent_Failure() {
        CompletableFuture<SendResult<String, Object>> future = new CompletableFuture<>();
        future.completeExceptionally(new RuntimeException("Kafka send failed"));

        when(kafkaTemplate.send(eq(orderEventTopic), eq(event))).thenReturn(future);

        kafkaProducer.sendOrderEvent(event);

        verify(kafkaTemplate).send(eq(orderEventTopic), eq(event));
    }
}

