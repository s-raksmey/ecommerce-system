package com.example.order.persistence.adapter;

import com.example.entity.Order;
import com.example.order.persistence.mapper.OrderDataAccessMapper;
import com.example.order.persistence.repository.OrderJpaRepository;
import com.example.port.ouput.OrderRepository;
import org.springframework.stereotype.Component;

@Component
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderDataAccessMapper orderDataAccessMapper;

    public OrderRepositoryAdapter(
            OrderJpaRepository orderJpaRepository,
            OrderDataAccessMapper orderDataAccessMapper
    ) {
        this.orderJpaRepository = orderJpaRepository;
        this.orderDataAccessMapper = orderDataAccessMapper;
    }

    @Override
    public Order saveOrder(Order order) {
        var orderEntity = orderDataAccessMapper.orderToOrderEntity(order);
        var savedOrderEntity = orderJpaRepository.save(orderEntity);
        return orderDataAccessMapper.orderEntityToOrder(savedOrderEntity);
    }

}
