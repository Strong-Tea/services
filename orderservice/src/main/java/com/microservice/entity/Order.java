package com.microservice.entity;

import com.microservice.enums.Status;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.util.UUID;

/**
 * Класс представляет собой модель заказов.
 * Заказы связаны с сервисом уведомлений.
 */
@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    /**
     * Идентификатор заказа.
     */
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    UUID id;

    /**
     * Идентификатор покупателя.
     */
    @Column(name = "customer_id", nullable = false)
    UUID customerId;

    /**
     * Идентификатор товара.
     */
    @Column(name = "product_id", nullable = false)
    UUID productId;

    /**
     * Количество товара.
     */
    @Column(name = "order_quantity", nullable = false)
    Long orderQuantity;

    /**
     * Статус заказа.
     */
    @Column(name = "order_status", nullable = false)
    Status orderStatus;
}
