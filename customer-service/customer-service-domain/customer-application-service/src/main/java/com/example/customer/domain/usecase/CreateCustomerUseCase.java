package com.example.customer.domain.usecase;

import com.example.customer.client.dto.UserResponse;
import com.example.customer.client.exception.UserServiceUnavailableException;
import com.example.customer.client.feign.UserFeignClient;
import com.example.customer.domain.dto.CreateCustomerCommand;
import com.example.customer.domain.dto.CreateCustomerResult;
import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.event.CustomerCreatedEvent;
import com.example.customer.domain.exception.CustomerDomainException;
import com.example.customer.domain.mapper.CustomerDomainMapper;
import com.example.customer.domain.port.output.CustomerRepository;
import com.example.customer.domain.service.CustomerDomainService;
import feign.FeignException;
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
    private final UserFeignClient userFeignClient;

    @Transactional
    public CreateCustomerResult execute(CreateCustomerCommand createCustomerCommand) {
        log.info("Execute CreateCustomerUseCase : {}", createCustomerCommand);

        try {
            UserResponse userResponse = userFeignClient.getById(createCustomerCommand.userId());
            log.info("User found: {}", userResponse);
        } catch (FeignException.NotFound e) {
            throw new CustomerDomainException("User not found");
        } catch (UserServiceUnavailableException e) {
            throw new CustomerDomainException(e.getMessage());
        }

        if (customerRepository.existsByUsername(createCustomerCommand.username())) {
            throw new CustomerDomainException("Username already exists");
        }
        if (customerRepository.existsByEmail(createCustomerCommand.email())) {
            throw new CustomerDomainException("Email already exists");
        }

        Customer customer = customerDomainMapper.createCustomerCommandToCustomer(createCustomerCommand);

        CustomerCreatedEvent customerCreatedEvent = customerDomainService.validateAndInitiateCustomer(customer);

        Customer savedCustomer = customerRepository.save(customer);

        log.info("Customer created with id: {} at {}",
                savedCustomer.getId().value(), customerCreatedEvent.getCreatedAt());

        return new CreateCustomerResult(savedCustomer.getId().value());
    }
}
