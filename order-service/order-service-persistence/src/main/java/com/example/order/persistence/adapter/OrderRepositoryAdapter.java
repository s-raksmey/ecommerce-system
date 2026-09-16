package com.example.order.persistence.adapter;

import com.example.entity.Order;
import com.example.port.ouput.OrderRepository;
import com.example.order.persistence.repository.OrderJpaRepository;

public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    public OrderRepositoryAdapter(OrderJpaRepository orderJpaRepository) {
        this.orderJpaRepository = orderJpaRepository;
    }

    @Override
    public Order saveOrder(Order order) {
        // Map Order to OrderEntity
        // Map OrderEntity to Order
        return null;
    }

}
