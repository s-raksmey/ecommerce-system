package com.example.customer.domain.port.output;

import com.example.customer.domain.entity.Customer;
import com.example.domain.valueobject.CustomerId;

import java.util.Optional;

public interface CustomerRepository {

    Customer save(Customer customer);

    Optional<Customer> findById(CustomerId customerId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
