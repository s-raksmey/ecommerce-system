package com.example.order.domain.port.ouput;

import com.example.order.domain.entity.Customer;

import java.util.Optional;
import java.util.UUID;

/**
 * Output port used by the create-order use case to confirm the customer exists.
 * {@code CustomerRepositoryAdapter} is the driven adapter.
 */
public interface CustomerRepository {

    Optional<Customer> findCustomer(UUID customerId);

}
