package com.example.payment.restapi.mapper;

import com.example.payment.domain.dto.CreatePaymentCommand;
import com.example.payment.domain.dto.CreatePaymentResult;
import com.example.payment.restapi.dto.PaymentCreateRequest;
import com.example.payment.restapi.dto.PaymentCreateResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentWebMapper {
    CreatePaymentCommand paymentCreateRequestToCreatePaymentCommand(PaymentCreateRequest paymentCreateRequest);

    PaymentCreateResponse createPaymentResultToPaymentCreateResponse(CreatePaymentResult createPaymentResult);
}
