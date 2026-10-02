package com.example.order.domain.service;

import com.example.order.domain.entity.Business;
import com.example.order.domain.entity.Order;
import com.example.order.domain.event.OrderCancelledEvent;
import com.example.order.domain.event.OrderCreatedEvent;
import com.example.order.domain.event.OrderPaidEvent;
import com.example.order.domain.exception.OrderDomainException;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

public class OrderDomainServiceImpl implements OrderDomainService {

    @Override
    public OrderCreatedEvent validateAndInitiateOrder(Order order, Business business) {
        validateBusiness(business);
        setOrderProductInformation(order, business);
        order.validateOrder();
        order.initializeOrder();
        return new OrderCreatedEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public OrderPaidEvent payOrder(Order order) {
        order.pay();
        return new OrderPaidEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void approveOrder(Order order) {
        order.approve();
    }

    @Override
    public OrderCancelledEvent cancelOrderPayment(Order order, List<String> failureMessages) {
        order.initCancel(failureMessages);
        return new OrderCancelledEvent(order, ZonedDateTime.now(ZoneId.of("UTC")));
    }

    @Override
    public void cancelOrder(Order order, List<String> failureMessages) {
        order.cancel(failureMessages);
    }

    private void validateBusiness(Business business) {
        if (!business.isActive()) {
            throw new OrderDomainException(
                    "Business with ID: " + business.getId().value() + " is not currently active"
            );
        }
    }

    private void setOrderProductInformation(Order order, Business business) {
        order.getItems().forEach(orderItem -> business.getProducts().forEach(businessProduct -> {
            if (businessProduct.getId().equals(orderItem.getProduct().getId())) {
                orderItem.getProduct().updateWithConfirmedNameAndPrice(
                        businessProduct.getName(),
                        businessProduct.getPrice()
                );
            }
        }));
    }

}
