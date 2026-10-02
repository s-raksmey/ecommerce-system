package com.example.customer.domain.usecase;

import com.example.customer.domain.dto.CreateCustomerCommand;
import com.example.customer.domain.dto.CreateCustomerResult;
import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.exception.CustomerDomainException;
import com.example.customer.domain.port.output.CustomerRepository;
import com.example.customer.domain.service.CustomerDomainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CreateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    @Transactional
    public CreateCustomerResult execute(CreateCustomerCommand createCustomerCommand) {
        log.info("executing CreateCustomerUseCase: {}", createCustomerCommand);

        Customer customer = Customer.builder()
                .username(createCustomerCommand.username())
                .familyName(createCustomerCommand.familyName())
                .givenName(createCustomerCommand.givenName())
                .build();

        customerDomainService.validateAndInitializeCustomer(customer);

        Customer savedCustomer = customerRepository.saveCustomer(customer);
        if (savedCustomer == null) {
            throw new CustomerDomainException("Could not save customer into database");
        }

        return new CreateCustomerResult(savedCustomer.getId().value());
    }
}
