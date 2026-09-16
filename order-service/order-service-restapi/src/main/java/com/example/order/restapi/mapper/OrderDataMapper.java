package com.example.order.restapi.mapper;

import com.example.dto.CreateOrderItemRequest;
import com.example.dto.CreateOrderRequest;
import com.example.order.restapi.dto.OrderCreateRequest;
import com.example.order.restapi.dto.OrderItemRequest;
import com.example.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class OrderDataMapper {

    public CreateOrderRequest orderCreateRequestToCreateOrderRequest(OrderCreateRequest orderCreateRequest) {
        return new CreateOrderRequest(
                new CustomerId(orderCreateRequest.customerId()),
                new BusinessId(orderCreateRequest.businessId()),
                new StreetAddress(
                        UUID.randomUUID(),
                        orderCreateRequest.orderAddress().street(),
                        orderCreateRequest.orderAddress().postalCode(),
                        orderCreateRequest.orderAddress().city()
                ),
                new Money(orderCreateRequest.price()),
                orderItemRequestsToCreateOrderItemRequests(orderCreateRequest.items())
        );
    }

    private List<CreateOrderItemRequest> orderItemRequestsToCreateOrderItemRequests(
            List<OrderItemRequest> orderItemRequests
    ) {
        return orderItemRequests.stream()
                .map(this::orderItemRequestToCreateOrderItemRequest)
                .toList();
    }

    private CreateOrderItemRequest orderItemRequestToCreateOrderItemRequest(OrderItemRequest orderItemRequest) {
        return new CreateOrderItemRequest(
                new ProductId(orderItemRequest.productId()),
                orderItemRequest.quantity(),
                new Money(orderItemRequest.price()),
                new Money(orderItemRequest.subTotal())
        );
    }

}
