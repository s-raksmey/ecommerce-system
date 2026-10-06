package com.example.order.restapi.controller;

import com.example.order.domain.dto.CreateOrderCommand;
import com.example.order.domain.dto.CreateOrderResult;
import com.example.order.domain.usecase.CreateOrderUseCase;
import com.example.order.restapi.dto.OrderCreateRequest;
import com.example.order.restapi.dto.OrderCreateResponse;
import com.example.order.restapi.mapper.OrderWebMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Driving adapter for the create-order use case.
 * Keeps HTTP here: the request becomes a {@link CreateOrderCommand}, and the
 * {@link CreateOrderResult} becomes the HTTP response. Domain rules stay in the use case.
 */
@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderCommandController {

    private final CreateOrderUseCase createOrderUseCase;
    private final OrderWebMapper orderWebMapper;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderCreateResponse createOrder(
            @Valid @RequestBody OrderCreateRequest orderCreateRequest
    ) {
        CreateOrderCommand createOrderCommand = orderWebMapper
                .orderCreateRequestToCreateOrderCommand(orderCreateRequest);

        CreateOrderResult createOrderResult = createOrderUseCase.execute(createOrderCommand);

        return orderWebMapper.createOrderResultToOrderCreateResponse(createOrderResult);
    }

}
