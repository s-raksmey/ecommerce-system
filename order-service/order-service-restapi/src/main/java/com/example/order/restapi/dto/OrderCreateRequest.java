package com.example.order.restapi.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * HTTP body for POST /api/v1/orders. Validation stays at the adapter.
 * {@link com.example.order.restapi.mapper.OrderWebMapper} converts this into the use-case command.
 */
@Builder
public record OrderCreateRequest(
        @NotNull
        UUID customerId,
        @NotNull
        UUID businessId,
        @NotNull
        @Valid
        OrderAddressRequest orderAddress,
        @NotNull
        @NotEmpty
        @Valid
        List<OrderItemRequest> items,
        @NotNull
        BigDecimal price
) {
}
