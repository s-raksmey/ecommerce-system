package com.example;

import com.example.entity.Business;
import com.example.entity.Order;
import com.example.event.OrderCancelledEvent;
import com.example.event.OrderCreatedEvent;
import com.example.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {

    OrderCreatedEvent validateAndInitiateOrder(Order order, Business business);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
