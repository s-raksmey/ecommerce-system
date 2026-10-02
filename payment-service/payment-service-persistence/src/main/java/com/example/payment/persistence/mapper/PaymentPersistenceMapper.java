package com.example.payment.persistence.mapper;

import com.example.payment.domain.entity.Payment;
import com.example.payment.persistence.entity.PaymentEntity;
import com.example.valueobject.CustomerId;
import com.example.valueobject.Money;
import com.example.valueobject.OrderId;
import com.example.valueobject.PaymentId;
import org.springframework.stereotype.Component;

@Component
public class PaymentPersistenceMapper {

    public PaymentEntity paymentToPaymentEntity(Payment payment) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setId(payment.getId().value());
        paymentEntity.setOrderId(payment.getOrderId().value());
        paymentEntity.setCustomerId(payment.getCustomerId().value());
        paymentEntity.setPrice(payment.getPrice().getAmount());
        paymentEntity.setPaymentStatus(payment.getPaymentStatus());
        return paymentEntity;
    }

    public Payment paymentEntityToPayment(PaymentEntity paymentEntity) {
        return Payment.builder()
                .id(new PaymentId(paymentEntity.getId()))
                .orderId(new OrderId(paymentEntity.getOrderId()))
                .customerId(new CustomerId(paymentEntity.getCustomerId()))
                .price(new Money(paymentEntity.getPrice()))
                .paymentStatus(paymentEntity.getPaymentStatus())
                .build();
    }
}
