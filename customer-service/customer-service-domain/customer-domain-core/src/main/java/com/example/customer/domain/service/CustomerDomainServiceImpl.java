package com.example.customer.domain.service;

import com.example.customer.domain.entity.Customer;

public class CustomerDomainServiceImpl implements CustomerDomainService {
    @Override
    public void validateAndInitializeCustomer(Customer customer) {
        customer.validateCustomer();
        customer.initializeCustomer();
    }
}
