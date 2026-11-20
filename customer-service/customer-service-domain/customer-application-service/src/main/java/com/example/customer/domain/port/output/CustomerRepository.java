package com.example.customer.domain.port.output;

import com.example.core.PageResponse;
import com.example.customer.domain.dto.CustomerResult;
import com.example.customer.domain.entity.Customer;
import com.example.domain.valueobject.CustomerId;

import java.util.Optional;

public interface CustomerRepository {

    PageResponse<CustomerResult> findAll(int page, int size);

    Customer save(Customer customer);

    Optional<Customer> findById(CustomerId customerId);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

}
