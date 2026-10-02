package com.example.payment.restapi.dto;

import com.example.valueobject.PaymentStatus;
import lombok.Builder;

import java.util.UUID;

@Builder
public record PaymentCreateResponse(
        UUID paymentId,
        PaymentStatus paymentStatus
) {
}
