package com.example.payment.domain.port.output;

import com.example.payment.domain.entity.Payment;

public interface PaymentRepository {
    Payment savePayment(Payment payment);
}
