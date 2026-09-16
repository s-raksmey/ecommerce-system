package com.example.order.restapi.controller;

import com.example.order.restapi.dto.OrderCreateRequest;
import com.example.order.restapi.dto.OrderCreateResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public OrderCreateResponse createOrder(
            @Valid @RequestBody OrderCreateRequest orderCreateRequest
    ) {
        return OrderCreateResponse.builder()
                .orderId(UUID.randomUUID())
                .build();
    }

}
