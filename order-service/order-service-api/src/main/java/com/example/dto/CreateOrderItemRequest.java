package com.example.dto;

import com.example.valueobject.Money;
import com.example.valueobject.ProductId;

public record CreateOrderItemRequest(
        ProductId productId,
        Integer quantity,
        Money price,
        Money subTotal
) {
}
