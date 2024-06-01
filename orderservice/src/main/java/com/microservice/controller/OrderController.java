package com.microservice.controller;

import com.microservice.entity.Order;
import com.microservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Tag(name = "Контроллер для операций с заказами")
@RestController
@Slf4j
@RequestMapping("/api/v1/orderservice/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Operation(summary = "Получить заказ по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Успешная операция",
            content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "404", description = "Заказ не найден")
    })
    @GetMapping("/order/{id}")
    public Order getOrder(@PathVariable UUID id) {
        log.info("Get order with id {}", id);
        return orderService.getOrderById(id);
    }


    @Operation(summary = "Метод создания заказа")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Заказ успешно создан",
            content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @PostMapping
    public Order createOrder(@RequestBody Order order) {
        log.info("Create order {}", order);
        return orderService.createOrder(order);
    }


    @Operation(summary = "Метод обновления заказа по ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Заказ успешно обновлен",
            content = @Content(schema = @Schema(implementation = Order.class))),
        @ApiResponse(responseCode = "404", description = "Заказ не найден"),
        @ApiResponse(responseCode = "400", description = "Некорректные данные запроса")
    })
    @PatchMapping
    public Order updateOrder(@RequestBody Order order) {
        log.info("Update order {}", order);
        return orderService.updateOrder(order);
    }
}

