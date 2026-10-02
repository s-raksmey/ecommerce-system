package com.example.payment.restapi.controller;

import com.example.payment.domain.dto.CreatePaymentCommand;
import com.example.payment.domain.dto.CreatePaymentResult;
import com.example.payment.domain.usecase.CreatePaymentUseCase;
import com.example.payment.restapi.dto.PaymentCreateRequest;
import com.example.payment.restapi.dto.PaymentCreateResponse;
import com.example.payment.restapi.mapper.PaymentWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentCommandController {

    private final CreatePaymentUseCase createPaymentUseCase;
    private final PaymentWebMapper paymentWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public PaymentCreateResponse createPayment(
            @Valid @RequestBody PaymentCreateRequest paymentCreateRequest
    ) {
        CreatePaymentCommand createPaymentCommand =
                paymentWebMapper.paymentCreateRequestToCreatePaymentCommand(paymentCreateRequest);
        CreatePaymentResult createPaymentResult = createPaymentUseCase.execute(createPaymentCommand);
        return paymentWebMapper.createPaymentResultToPaymentCreateResponse(createPaymentResult);
    }
}
