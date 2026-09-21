package com.example.order.domain.dto;

import java.util.UUID;

public record CreateOrderResult(
        UUID orderId
) {
}
