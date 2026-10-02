package com.example.payment.domain.usecase;

import com.example.payment.domain.dto.CreatePaymentCommand;
import com.example.payment.domain.dto.CreatePaymentResult;
import com.example.payment.domain.entity.Payment;
import com.example.payment.domain.exception.PaymentDomainException;
import com.example.payment.domain.port.output.PaymentRepository;
import com.example.payment.domain.service.PaymentDomainService;
import com.example.valueobject.CustomerId;
import com.example.valueobject.Money;
import com.example.valueobject.OrderId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreatePaymentUseCase {

    private final PaymentDomainService paymentDomainService;
    private final PaymentRepository paymentRepository;

    @Transactional
    public CreatePaymentResult execute(CreatePaymentCommand createPaymentCommand) {
        log.info("executing CreatePaymentUseCase: {}", createPaymentCommand);

        Payment payment = Payment.builder()
                .orderId(new OrderId(createPaymentCommand.orderId()))
                .customerId(new CustomerId(createPaymentCommand.customerId()))
                .price(new Money(createPaymentCommand.price()))
                .build();

        paymentDomainService.validateAndTakePayment(payment);

        Payment savedPayment = paymentRepository.savePayment(payment);
        if (savedPayment == null) {
            throw new PaymentDomainException("Could not save payment into database");
        }

        return new CreatePaymentResult(savedPayment.getId().value(), savedPayment.getPaymentStatus());
    }
}
