package com.example.order.persistence.adapter;

import com.example.order.domain.entity.Order;
import com.example.order.domain.port.ouput.OrderRepository;
import com.example.order.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;

    @Override
    public Order saveOrder(Order order) {
        return null;
    }

}
