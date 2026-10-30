package com.example.order.domain.dto;

import com.example.domain.valueobject.OrderId;

import java.util.UUID;

public record CreateOrderResult(
        UUID orderId
) {
}
