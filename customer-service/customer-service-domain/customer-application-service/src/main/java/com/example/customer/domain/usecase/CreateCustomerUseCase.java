package com.example.customer.domain.usecase;

import com.example.customer.domain.dto.CreateCustomerCommand;
import com.example.customer.domain.dto.CreateCustomerResult;
import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.event.CustomerCreatedEvent;
import com.example.customer.domain.exception.CustomerDomainException;
import com.example.customer.domain.mapper.CustomerDomainMapper;
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
    private final CustomerDomainMapper customerDomainMapper;

    @Transactional
    public CreateCustomerResult execute(CreateCustomerCommand createCustomerCommand) {
        log.info("Execute CreateCustomerUseCase : {}", createCustomerCommand);

        if (customerRepository.existsByUsername(createCustomerCommand.username())) {
            throw new CustomerDomainException("Username already exists");
        }

        if (customerRepository.existsByEmail(createCustomerCommand.email())) {
            throw new CustomerDomainException("Email already exists");
        }

        Customer customer = customerDomainMapper.createCustomerCommandToCustomer(createCustomerCommand);
        CustomerCreatedEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);
        Customer savedCustomer = customerRepository.save(customer);

        if (savedCustomer == null) {
            log.error("Could not save customer with id: {}", createCustomerCommand.customerId());
            throw new CustomerDomainException("Could not save customer with id " + createCustomerCommand.customerId());
        }

        return new CreateCustomerResult(savedCustomer.getId().value());
    }
}
