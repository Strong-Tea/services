package com.microservice.enums;

/**
 * Перечисление представляет собой список статусов заказа.
 */

public enum Status {
    /**
     * Оформлен.
     */
    PROCESSED,

    /**
     * Ожидает оплату.
     */
    AWAITING_PAYMENT,

    /**
     * Оплачен.
     */
    PAID,

    /**
     * Подтвержден.
     */
    CONFIRMED
}
