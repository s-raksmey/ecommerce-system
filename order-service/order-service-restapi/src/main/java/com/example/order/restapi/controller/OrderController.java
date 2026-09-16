package com.example.order.restapi.controller;

import com.example.dto.CreateOrderResponse;
import com.example.order.restapi.dto.OrderCreateRequest;
import com.example.order.restapi.dto.OrderCreateResponse;
import com.example.order.restapi.mapper.OrderDataMapper;
import com.example.port.input.CreateOrderUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    private final CreateOrderUseCase createOrderUseCase;
    private final OrderDataMapper orderDataMapper;

    public OrderController(CreateOrderUseCase createOrderUseCase, OrderDataMapper orderDataMapper) {
        this.createOrderUseCase = createOrderUseCase;
        this.orderDataMapper = orderDataMapper;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderCreateResponse createOrder(
            @Valid @RequestBody OrderCreateRequest orderCreateRequest
    ) {
        CreateOrderResponse createOrderResponse = createOrderUseCase.execute(
                orderDataMapper.orderCreateRequestToCreateOrderRequest(orderCreateRequest)
        );

        return OrderCreateResponse.builder()
                .orderId(createOrderResponse.orderId().value())
                .build();
    }

}
