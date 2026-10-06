package com.example.order.domain.dto;

import java.util.UUID;

/**
 * Output of the create-order use case.
 * Carries the new order id back to the driving adapter, which maps it to the HTTP response.
 */
public record CreateOrderResult(
        UUID orderId
) {
}
