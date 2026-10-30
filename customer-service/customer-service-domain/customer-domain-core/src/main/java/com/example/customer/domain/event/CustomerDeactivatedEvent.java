package com.example.customer.domain.event;

import com.example.customer.domain.entity.Customer;
import com.example.domain.event.DomainEvent;
import com.example.domain.valueobject.CustomerId;

import java.time.ZonedDateTime;

public class CustomerDeactivatedEvent implements DomainEvent<Customer> {
    private final CustomerId customerId;
    private final ZonedDateTime deactivatedAt;

    public CustomerDeactivatedEvent(CustomerId customerId, ZonedDateTime deactivatedAt){
        this.customerId = customerId;
        this.deactivatedAt = deactivatedAt;
    }

    public CustomerId getCustomerId() {
        return customerId;
    }

    public ZonedDateTime getDeactivatedAt() {
        return deactivatedAt;
    }
}
