package com.example.dto;

import com.example.valueobject.BusinessId;
import com.example.valueobject.CustomerId;
import com.example.valueobject.Money;
import com.example.valueobject.StreetAddress;

import java.util.List;

public record CreateOrderRequest(
        CustomerId customerId,
        BusinessId businessId,
        StreetAddress deliveryAddress,
        Money price,
        List<CreateOrderItemRequest> items
) {
}
