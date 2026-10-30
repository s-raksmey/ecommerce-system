package com.example.customer.domain.usecase;

import com.example.customer.domain.dto.UpdateCustomerCommand;
import com.example.customer.domain.dto.UpdateCustomerResult;
import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.event.CustomerUpdatedEvent;
import com.example.customer.domain.exception.CustomerDomainException;
import com.example.customer.domain.mapper.CustomerDomainMapper;
import com.example.customer.domain.port.output.CustomerRepository;
import com.example.customer.domain.service.CustomerDomainService;
import com.example.domain.valueobject.CustomerId;
import com.example.domain.valueobject.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class UpdateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;
    private final CustomerDomainMapper customerDomainMapper;

    @Transactional
    public UpdateCustomerResult execute(UpdateCustomerCommand updateCustomerCommand) {

        Customer customer = customerRepository.findById(new CustomerId(updateCustomerCommand.customerId()))
                .orElseThrow(() -> new CustomerDomainException(
                        "Customer not found: " + updateCustomerCommand.customerId()));

        Email newEmail = new Email(updateCustomerCommand.email());

        if (!newEmail.equals(customer.getEmail()) && customerRepository.existsByEmail(newEmail.value())) {
            throw new CustomerDomainException("Email already exists");
        }

        CustomerUpdatedEvent customerUpdatedEvent = customerDomainService.updateCustomer(customer);
        Customer savedCustomer = customerRepository.save(customer);

        log.info("Customer updated with id: {} at {}",
                savedCustomer.getId().value(), customerUpdatedEvent.getUpdatedAt());
        return new UpdateCustomerResult(savedCustomer.getId().value());
    }
}
