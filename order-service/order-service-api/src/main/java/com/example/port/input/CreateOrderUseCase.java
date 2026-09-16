package com.example.port.input;

import com.example.dto.CreateOrderRequest;
import com.example.dto.CreateOrderResponse;

public interface CreateOrderUseCase {

    CreateOrderResponse execute(CreateOrderRequest createOrderRequest);

}
