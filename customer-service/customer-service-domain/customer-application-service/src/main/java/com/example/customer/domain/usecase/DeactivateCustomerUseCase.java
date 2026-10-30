package com.example.customer.domain.usecase;

import com.example.customer.domain.dto.DeactivateCustomerCommand;
import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.event.CustomerDeactivatedEvent;
import com.example.customer.domain.exception.CustomerDomainException;
import com.example.customer.domain.port.output.CustomerRepository;
import com.example.customer.domain.service.CustomerDomainService;
import com.example.domain.valueobject.CustomerId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@RequiredArgsConstructor
public class DeactivateCustomerUseCase {

    private final CustomerDomainService customerDomainService;
    private final CustomerRepository customerRepository;

    @Transactional
    public void execute(DeactivateCustomerCommand deactivateCustomerCommand) {
        log.info("Execute DeactivateCustomerUseCase : {}", deactivateCustomerCommand);

        Customer customer = customerRepository.findById(new CustomerId(deactivateCustomerCommand.customerId()))
                .orElseThrow(() -> new CustomerDomainException(
                        "Customer not found: " + deactivateCustomerCommand.customerId()));

        CustomerDeactivatedEvent customerDeactivatedEvent = customerDomainService.deactivateCustomer(customer);
        customerRepository.save(customer);

        log.info("Customer deactivated with id: {} at {}",
                customerDeactivatedEvent.getCustomerId().value(), customerDeactivatedEvent.getDeactivatedAt());
    }
}
