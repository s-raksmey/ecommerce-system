package com.example.payment.domain.mapper;

import com.example.payment.domain.dto.CreatePaymentCommand;
import com.example.payment.domain.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentDomainMapper {
    @Mapping(source = "orderId", target = "orderId.value")
    @Mapping(source = "customerId", target = "customerId.value")
    @Mapping(source = "price", target = "price.amount")
    Payment createPaymentCommandToPayment(CreatePaymentCommand createPaymentCommand);
}
