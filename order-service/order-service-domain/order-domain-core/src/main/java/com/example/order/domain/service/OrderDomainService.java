package com.example.order.domain.service;

import com.example.order.domain.entity.Business;
import com.example.order.domain.entity.Order;
import com.example.order.domain.event.OrderCancelledEvent;
import com.example.order.domain.event.OrderCreatedEvent;
import com.example.order.domain.event.OrderPaidEvent;

import java.util.List;

public interface OrderDomainService {
    /**
     * Create-order domain step. Rejects an inactive business, confirms each item
     * against the business catalog, then validates and initializes the order.
     */
    OrderCreatedEvent validateAndInitiateOrder(Order order, Business business);

    OrderPaidEvent payOrder(Order order);

    void approveOrder(Order order);

    OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages);

    void cancelOrder(Order order, List<String> failureMessages);
}
