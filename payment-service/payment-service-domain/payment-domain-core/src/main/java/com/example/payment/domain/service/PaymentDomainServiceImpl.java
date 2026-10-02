package com.example.payment.domain.service;

import com.example.payment.domain.entity.Payment;

public class PaymentDomainServiceImpl implements PaymentDomainService {
    @Override
    public void validateAndTakePayment(Payment payment) {
        payment.validatePayment();
        payment.initializePayment();
        payment.complete();
    }
}
