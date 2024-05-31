package com.microservice.service;

import com.microservice.entity.Order;
import com.microservice.exception.NotFoundException;
import com.microservice.exception.SuchOrderAlreadyExist;
import com.microservice.exception.SuchOrderNotExist;
import com.microservice.dto.OrderEventDto;
import com.microservice.enums.OrderEvent;
import com.microservice.kafka.impl.KafkaProducerImpl;
import com.microservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final KafkaProducerImpl kafkaProducer;
    private final OrderRepository orderRepository;

    public Order getOrderById(UUID id) throws NotFoundException {
        return orderRepository.findById(id)
            .orElseThrow(() -> new NotFoundException(
                String.format("Order with id: %s was not found", id)
            ));
    }

    public Order createOrder(Order order) throws SuchOrderAlreadyExist {
        if (order.getId() != null && orderRepository.existsById(order.getId())) {
            throw new SuchOrderAlreadyExist("Such order already exist");
        }
        orderRepository.save(order);
        kafkaProducer.sendOrderEvent(new OrderEventDto(order.getId(), OrderEvent.CREATED));
        return order;
    }

    public Order updateOrder(Order order) throws SuchOrderNotExist {
        if (!orderRepository.existsById(order.getId())) {
            throw new SuchOrderNotExist("Such order not exist");
        }
        orderRepository.save(order);
        kafkaProducer.sendOrderEvent(new OrderEventDto(order.getId(), OrderEvent.UPDATED));
        return order;
    }
}
