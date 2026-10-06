package com.example.order.domain.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Input of the create-order use case.
 * The web mapper builds this from the HTTP request so the application layer
 * does not depend on the REST DTO.
 */
public record CreateOrderCommand(
        UUID customerId,
        UUID businessId,
        BigDecimal price,
        CommandOrderAddress deliveryAddress,
        List<CommandOrderItem> items
) {
}
