package com.example.payment.persistence.adapter;

import com.example.payment.domain.entity.Payment;
import com.example.payment.domain.port.output.PaymentRepository;
import com.example.payment.persistence.entity.PaymentEntity;
import com.example.payment.persistence.mapper.PaymentPersistenceMapper;
import com.example.payment.persistence.repository.PaymentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepository {

    private final PaymentJpaRepository paymentJpaRepository;
    private final PaymentPersistenceMapper paymentPersistenceMapper;

    @Override
    public Payment savePayment(Payment payment) {
        PaymentEntity paymentEntity = paymentPersistenceMapper.paymentToPaymentEntity(payment);
        PaymentEntity savedPaymentEntity = paymentJpaRepository.save(paymentEntity);
        return paymentPersistenceMapper.paymentEntityToPayment(savedPaymentEntity);
    }
}
