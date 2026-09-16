package com.example.order.persistence.mapper;

import com.example.entity.Order;
import com.example.entity.OrderItem;
import com.example.entity.Product;
import com.example.order.persistence.entity.OrderAddressEntity;
import com.example.order.persistence.entity.OrderEntity;
import com.example.order.persistence.entity.OrderItemEntity;
import com.example.valueobject.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Component
public class OrderDataAccessMapper {

    public OrderEntity orderToOrderEntity(Order order) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.getId().value());
        orderEntity.setCustomerId(order.getCustomerId().value());
        orderEntity.setBusinessId(order.getBusinessId().value());
        orderEntity.setPrice(order.getPrice().amount());
        orderEntity.setTrackingId(order.getTrackingId().value());
        orderEntity.setOrderStatus(order.getOrderStatus());
        orderEntity.setFailureMessages(failureMessagesToString(order.getFailureMessages()));

        OrderAddressEntity orderAddressEntity = orderAddressToOrderAddressEntity(
                order.getDeliveryAddress(),
                orderEntity
        );
        orderEntity.setOrderAddress(orderAddressEntity);

        List<OrderItemEntity> orderItemEntities = order.getItems().stream()
                .map(orderItem -> orderItemToOrderItemEntity(orderItem, orderEntity))
                .toList();
        orderEntity.setItems(orderItemEntities);

        return orderEntity;
    }

    public Order orderEntityToOrder(OrderEntity orderEntity) {
        return Order.builder()
                .id(new OrderId(orderEntity.getId()))
                .customerId(new CustomerId(orderEntity.getCustomerId()))
                .businessId(new BusinessId(orderEntity.getBusinessId()))
                .deliveryAddress(orderAddressEntityToOrderAddress(orderEntity.getOrderAddress()))
                .price(new Money(orderEntity.getPrice()))
                .items(orderItemEntitiesToOrderItems(orderEntity.getItems()))
                .trackingId(new TrackingId(orderEntity.getTrackingId()))
                .orderStatus(orderEntity.getOrderStatus())
                .failureMessages(failureMessagesToList(orderEntity.getFailureMessages()))
                .build();
    }

    private OrderAddressEntity orderAddressToOrderAddressEntity(
            StreetAddress streetAddress,
            OrderEntity orderEntity
    ) {
        OrderAddressEntity orderAddressEntity = new OrderAddressEntity();
        orderAddressEntity.setId(streetAddress.id());
        orderAddressEntity.setStreet(streetAddress.street());
        orderAddressEntity.setPostalCode(streetAddress.postalCode());
        orderAddressEntity.setCity(streetAddress.city());
        orderAddressEntity.setOrder(orderEntity);
        return orderAddressEntity;
    }

    private StreetAddress orderAddressEntityToOrderAddress(OrderAddressEntity orderAddressEntity) {
        return new StreetAddress(
                orderAddressEntity.getId(),
                orderAddressEntity.getStreet(),
                orderAddressEntity.getPostalCode(),
                orderAddressEntity.getCity()
        );
    }

    private OrderItemEntity orderItemToOrderItemEntity(OrderItem orderItem, OrderEntity orderEntity) {
        OrderItemEntity orderItemEntity = new OrderItemEntity();
        if (orderItem.getId() != null) {
            orderItemEntity.setId(orderItem.getId().value());
        }
        orderItemEntity.setProductId(orderItem.getProduct().getId().value());
        orderItemEntity.setQuantity(orderItem.getQuantity());
        orderItemEntity.setPrice(orderItem.getPrice().amount());
        orderItemEntity.setSubTotal(orderItem.getSubTotal().amount());
        orderItemEntity.setOrder(orderEntity);
        return orderItemEntity;
    }

    private List<OrderItem> orderItemEntitiesToOrderItems(List<OrderItemEntity> orderItemEntities) {
        if (orderItemEntities == null) {
            return List.of();
        }
        return orderItemEntities.stream()
                .map(this::orderItemEntityToOrderItem)
                .toList();
    }

    private OrderItem orderItemEntityToOrderItem(OrderItemEntity orderItemEntity) {
        Product product = Product.builder()
                .id(new ProductId(orderItemEntity.getProductId()))
                .name("product")
                .price(new Money(orderItemEntity.getPrice()))
                .build();

        return OrderItem.builder()
                .id(orderItemEntity.getId() != null ? new OrderItemId(orderItemEntity.getId()) : null)
                .orderId(orderItemEntity.getOrder() != null
                        ? new OrderId(orderItemEntity.getOrder().getId())
                        : null)
                .product(product)
                .quantity(orderItemEntity.getQuantity())
                .price(new Money(orderItemEntity.getPrice()))
                .subTotal(new Money(orderItemEntity.getSubTotal()))
                .build();
    }

    private String failureMessagesToString(List<String> failureMessages) {
        if (failureMessages == null || failureMessages.isEmpty()) {
            return null;
        }
        return String.join(";", failureMessages);
    }

    private List<String> failureMessagesToList(String failureMessages) {
        if (failureMessages == null || failureMessages.isBlank()) {
            return List.of();
        }
        return Arrays.asList(failureMessages.split(";"));
    }

}
