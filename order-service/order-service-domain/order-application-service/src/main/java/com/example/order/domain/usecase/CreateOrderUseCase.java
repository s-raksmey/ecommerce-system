package com.example.order.domain.usecase;

import com.example.order.domain.dto.CreateOrderCommand;
import com.example.order.domain.dto.CreateOrderResult;
import com.example.order.domain.exception.OrderDomainException;
import com.example.order.domain.port.ouput.BusinessRepository;
import com.example.order.domain.port.ouput.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateOrderUseCase {

    private final CustomerRepository customerRepository;
    private final BusinessRepository businessRepository;

    public CreateOrderResult execute(CreateOrderCommand createOrderCommand) {
        log.info("executing CreateOrderUseCase: {}", createOrderCommand);

        customerRepository.findCustomer(createOrderCommand.customerId())
                .orElseThrow(() -> new OrderDomainException(
                        "Could not find customer with customer id: " + createOrderCommand.customerId()
                ));

        businessRepository.findBusiness(createOrderCommand.businessId())
                .orElseThrow(() -> new OrderDomainException(
                        "Could not find business with business id: " + createOrderCommand.businessId()
                ));

        return new CreateOrderResult(UUID.randomUUID());
    }

}
