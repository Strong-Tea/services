package com.microservice.dto;

import com.microservice.enums.OrderEvent;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Schema(description = "DTO для событий заказа")
public record OrderEventDto(
    @Schema(description = "Уникальный идентификатор заказа")
    UUID orderId,
    @Schema(description = "Событие заказа")
    OrderEvent orderEvent
) {
}
