package com.example.order.domain.port.ouput;

import com.example.order.domain.entity.Order;

public interface OrderRepository {

    Order saveOrder(Order order);

}
