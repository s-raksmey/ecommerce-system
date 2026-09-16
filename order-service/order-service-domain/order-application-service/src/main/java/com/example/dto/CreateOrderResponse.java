package com.example.dto;

import com.example.valueobject.OrderId;

public record CreateOrderResponse(
        OrderId orderId
) {
}
