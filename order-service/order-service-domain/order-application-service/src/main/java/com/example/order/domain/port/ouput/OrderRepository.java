package com.example.order.domain.port.ouput;

import com.example.order.domain.entity.Order;

/**
 * Output port for saving an order that the create-order use case has already initiated.
 * {@code OrderRepositoryAdapter} in the persistence module is the driven adapter.
 */
public interface OrderRepository {

    Order saveOrder(Order order);

}
