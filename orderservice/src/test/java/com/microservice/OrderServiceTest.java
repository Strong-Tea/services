package com.microservice;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;

import com.microservice.dto.OrderEventDto;
import com.microservice.entity.Order;
import com.microservice.enums.OrderEvent;
import com.microservice.exception.NotFoundException;
import com.microservice.exception.SuchOrderAlreadyExist;
import com.microservice.exception.SuchOrderNotExist;
import com.microservice.kafka.impl.KafkaProducerImpl;
import com.microservice.repository.OrderRepository;
import com.microservice.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private KafkaProducerImpl kafkaProducer;

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderService orderService;

    private UUID orderId;
    private Order order;

    @BeforeEach
    void setUp() {
        orderId = UUID.randomUUID();
        order = new Order();
        order.setId(orderId);
    }

    @Test
    void testGetOrderById_OrderExists() throws NotFoundException {
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));

        Order foundOrder = orderService.getOrderById(orderId);

        assertEquals(order, foundOrder);
        verify(orderRepository).findById(orderId);
    }

    @Test
    void testGetOrderById_OrderDoesNotExist() {
        when(orderRepository.findById(orderId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.getOrderById(orderId));
        verify(orderRepository).findById(orderId);
    }

    @Test
    void testCreateOrder_NewOrder() throws SuchOrderAlreadyExist {
        when(orderRepository.existsById(orderId)).thenReturn(false);

        Order createdOrder = orderService.createOrder(order);

        assertEquals(order, createdOrder);
        verify(orderRepository).save(order);
        verify(kafkaProducer).sendOrderEvent(new OrderEventDto(orderId, OrderEvent.CREATED));
    }

    @Test
    void testCreateOrder_OrderAlreadyExists() {
        when(orderRepository.existsById(orderId)).thenReturn(true);

        assertThrows(SuchOrderAlreadyExist.class, () -> orderService.createOrder(order));
        verify(orderRepository, never()).save(order);
        verify(kafkaProducer, never()).sendOrderEvent(any(OrderEventDto.class));
    }

    @Test
    void testUpdateOrder_OrderExists() throws SuchOrderNotExist {
        when(orderRepository.existsById(orderId)).thenReturn(true);

        Order updatedOrder = orderService.updateOrder(order);

        assertEquals(order, updatedOrder);
        verify(orderRepository).save(order);
        verify(kafkaProducer).sendOrderEvent(new OrderEventDto(orderId, OrderEvent.UPDATED));
    }

    @Test
    void testUpdateOrder_OrderDoesNotExist() {
        when(orderRepository.existsById(orderId)).thenReturn(false);

        assertThrows(SuchOrderNotExist.class, () -> orderService.updateOrder(order));
        verify(orderRepository, never()).save(order);
        verify(kafkaProducer, never()).sendOrderEvent(any(OrderEventDto.class));
    }
}

