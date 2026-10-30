package com.example.order.domain.dto;

import com.example.domain.valueobject.BusinessId;
import com.example.domain.valueobject.CustomerId;
import com.example.domain.valueobject.Money;
import com.example.domain.valueobject.StreetAddress;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID customerId,
        UUID businessId,
        BigDecimal price,
        CommandOrderAddress deliveryAddress,
        List<CommandOrderItem> items
) {
}
