package com.example.customer.domain.port.output;

import com.example.customer.domain.entity.Customer;

public interface CustomerRepository {
    Customer saveCustomer(Customer customer);
}
