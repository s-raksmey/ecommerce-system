package com.example.payment.persistence.payment.adapter;

import com.example.payment.persistence.payment.mapper.PaymentPersistenceMapper;
import com.example.payment.persistence.payment.repository.PaymentJpaRepository;
import com.example.payment.domain.entity.Payment;
import com.example.payment.domain.ports.output.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryImpl implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;

    @Override
    public Payment save(Payment payment) {
        return paymentPersistenceMapper
                .paymentEntityToPayment(paymentJpaRepository
                        .save(paymentPersistenceMapper.paymentToPaymentEntity(payment)));
    }

    @Override
    public Optional<Payment> findByOrderId(UUID orderId) {
        return paymentJpaRepository.findByOrderId(orderId)
                .map(paymentPersistenceMapper::paymentEntityToPayment);
    }
}
