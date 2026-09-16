package com.example.mapper;

import com.example.dto.CreateOrderItemRequest;
import com.example.dto.CreateOrderRequest;
import com.example.entity.Business;
import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.entity.Product;

import java.util.List;

public class OrderApplicationMapper {

    public Order createOrderRequestToOrder(CreateOrderRequest createOrderRequest) {
        List<OrderItem> orderItems = createOrderRequest.items().stream()
                .map(this::createOrderItemRequestToOrderItem)
                .toList();

        return Order.builder()
                .customerId(createOrderRequest.customerId())
                .businessId(createOrderRequest.businessId())
                .deliveryAddress(createOrderRequest.deliveryAddress())
                .price(createOrderRequest.price())
                .items(orderItems)
                .build();
    }

    public Business createOrderRequestToBusiness(CreateOrderRequest createOrderRequest) {
        List<Product> products = createOrderRequest.items().stream()
                .map(this::createOrderItemRequestToProduct)
                .toList();

        return Business.builder()
                .id(createOrderRequest.businessId())
                .active(true)
                .products(products)
                .build();
    }

    private OrderItem createOrderItemRequestToOrderItem(CreateOrderItemRequest createOrderItemRequest) {
        return OrderItem.builder()
                .product(createOrderItemRequestToProduct(createOrderItemRequest))
                .quantity(createOrderItemRequest.quantity())
                .price(createOrderItemRequest.price())
                .subTotal(createOrderItemRequest.subTotal())
                .build();
    }

    private Product createOrderItemRequestToProduct(CreateOrderItemRequest createOrderItemRequest) {
        return Product.builder()
                .id(createOrderItemRequest.productId())
                .name("product")
                .price(createOrderItemRequest.price())
                .build();
    }

}
