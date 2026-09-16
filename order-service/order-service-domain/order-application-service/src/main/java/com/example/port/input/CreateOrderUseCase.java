package com.example.port.input;

import com.example.dto.CreateOrderRequest;

public interface CreateOrderUseCase {

    void execute(CreateOrderRequest createOrderRequest);

}
