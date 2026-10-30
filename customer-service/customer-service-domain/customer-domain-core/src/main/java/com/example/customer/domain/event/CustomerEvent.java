package com.example.customer.domain.event;

import com.example.customer.domain.entity.Customer;
import com.example.domain.event.DomainEvent;

public abstract class CustomerEvent implements DomainEvent<Customer> {
    private final Customer customer;

    public CustomerEvent(Customer customer){
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

}
