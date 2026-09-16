package com.example.service;

import com.example.entity.Business;
import com.example.entity.Order;
import com.example.event.OrderCreatedEvent;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Business business);

}
