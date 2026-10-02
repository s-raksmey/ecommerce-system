package com.example.payment.domain.service;

import com.example.payment.domain.entity.Payment;

public interface PaymentDomainService {
    void validateAndTakePayment(Payment payment);
}
