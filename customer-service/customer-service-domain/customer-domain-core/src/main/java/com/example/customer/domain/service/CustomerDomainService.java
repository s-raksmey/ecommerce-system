package com.example.customer.domain.service;

import com.example.customer.domain.entity.Customer;
import com.example.customer.domain.event.CustomerCreatedEvent;
import com.example.customer.domain.event.CustomerDeactivatedEvent;
import com.example.customer.domain.event.CustomerUpdatedEvent;

public interface CustomerDomainService {

    CustomerCreatedEvent validateAndInitiateCustomer(Customer customer);

    CustomerUpdatedEvent updateCustomer(Customer customer);

    CustomerDeactivatedEvent deactivateCustomer(Customer customer);

}
