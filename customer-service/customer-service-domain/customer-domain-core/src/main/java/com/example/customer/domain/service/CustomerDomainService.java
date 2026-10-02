package com.example.customer.domain.service;

import com.example.customer.domain.entity.Customer;

public interface CustomerDomainService {
    void validateAndInitializeCustomer(Customer customer);
}
