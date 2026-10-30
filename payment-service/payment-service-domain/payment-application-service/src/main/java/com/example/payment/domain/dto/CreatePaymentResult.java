package com.example.payment.domain.dto;

import com.example.domain.valueobject.PaymentStatus;

import java.util.UUID;

public record CreatePaymentResult(
        UUID paymentId,
        PaymentStatus paymentStatus
) {
}
