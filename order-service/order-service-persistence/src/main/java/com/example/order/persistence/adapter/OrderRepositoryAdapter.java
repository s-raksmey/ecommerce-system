package com.example.order.persistence.adapter;

import com.example.order.domain.entity.Order;
import com.example.order.domain.port.ouput.OrderRepository;
import com.example.order.persistence.entity.OrderEntity;
import com.example.order.persistence.mapper.OrderPersistenceMapper;
import com.example.order.persistence.repository.OrderJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * Driven adapter for {@link OrderRepository}.
 * Translates the domain {@link Order} to JPA entities and back. The use case never sees these entities.
 */
@Repository
@RequiredArgsConstructor
public class OrderRepositoryAdapter implements OrderRepository {

    private final OrderJpaRepository orderJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Order saveOrder(Order order) {
        OrderEntity orderEntity = orderPersistenceMapper.orderToOrderEntity(order);

        orderEntity.getOrderAddress().setOrder(orderEntity);
        orderEntity.getItems()
                .forEach(orderItemEntity -> orderItemEntity.setOrder(orderEntity));

        OrderEntity savedOrderEntity = orderJpaRepository.save(orderEntity);
        return orderPersistenceMapper.orderEntityToOrder(savedOrderEntity);
    }

}
