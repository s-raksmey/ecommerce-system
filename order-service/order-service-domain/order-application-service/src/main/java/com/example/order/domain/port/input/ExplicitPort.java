package com.example.order.domain.port.input;

import com.example.order.domain.dto.CreateOrderCommand;

public interface ExplicitPort {

    void execute(CreateOrderCommand createOrderCommand);

}
