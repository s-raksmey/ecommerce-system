package com.example.port.ouput;

import com.example.entity.Order;

public interface OrderRepository {

    Order saveOrder(Order order);

}
