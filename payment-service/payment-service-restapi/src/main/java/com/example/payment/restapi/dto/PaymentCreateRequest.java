package com.example.payment.restapi.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PaymentCreateRequest(
        @NotNull
        UUID orderId,
        @NotNull
        UUID customerId,
        @NotNull
        BigDecimal price
) {
}
