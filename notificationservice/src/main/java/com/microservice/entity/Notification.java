package com.microservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notification")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Notification {
    /**
     * Идентификатор извещения.
     */
    @Id
    @UuidGenerator
    @Column(name = "id", nullable = false)
    UUID id;

    /**
     * Идентификатор заказа.
     */
    @Column(name = "order_id", nullable = false)
    UUID orderId;

    /**
     * Сообщение извещения.
     */
    @Column(name = "message", nullable = false)
    String message;

    /**
     * Время извещения.
     */
    @Column(name = "timestamp", nullable = false)
    LocalDateTime timestamp;
}

