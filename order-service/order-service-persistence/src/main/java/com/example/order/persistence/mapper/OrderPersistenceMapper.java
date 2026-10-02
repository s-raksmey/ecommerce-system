package com.example.order.persistence.mapper;

import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderItem;
import com.example.order.persistence.entity.OrderAddressEntity;
import com.example.order.persistence.entity.OrderEntity;
import com.example.order.persistence.entity.OrderItemEntity;
import com.example.valueobject.StreetAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "businessId.value", target = "businessId")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "trackingId.value", target = "trackingId")
    @Mapping(source = "deliveryAddress", target = "orderAddress")
    @Mapping(source = "failureMessages", target = "failureMessages", qualifiedByName = "mapFailureMessages")
    OrderEntity orderToOrderEntity(Order order);

    @Mapping(target = "id", expression = "java(java.util.UUID.randomUUID())")
    @Mapping(target = "order", ignore = true)
    OrderAddressEntity deliveryAddressToOrderAddressEntity(StreetAddress deliveryAddress);

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "product.id.value", target = "productId")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "subTotal.amount", target = "subTotal")
    @Mapping(target = "order", ignore = true)
    OrderItemEntity orderItemToOrderItemEntity(OrderItem orderItem);

    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "customerId.value", source = "customerId")
    @Mapping(target = "businessId.value", source = "businessId")
    @Mapping(target = "price.amount", source = "price")
    @Mapping(target = "trackingId.value", source = "trackingId")
    @Mapping(target = "deliveryAddress", source = "orderAddress")
    @Mapping(target = "failureMessages", source = "failureMessages", qualifiedByName = "mapFailureMessagesToList")
    Order orderEntityToOrder(OrderEntity orderEntity);

    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "product.id.value", source = "productId")
    @Mapping(target = "product.name", constant = "product")
    @Mapping(target = "product.price.amount", source = "price")
    @Mapping(target = "price.amount", source = "price")
    @Mapping(target = "subTotal.amount", source = "subTotal")
    @Mapping(target = "orderId", ignore = true)
    OrderItem orderItemEntityToOrderItem(OrderItemEntity orderItemEntity);

    @Mapping(target = "id", source = "id")
    StreetAddress orderAddressEntityToStreetAddress(OrderAddressEntity orderAddressEntity);

    @Named("mapFailureMessages")
    default String mapFailureMessages(List<String> failureMessages) {
        return failureMessages == null ? "" : String.join(",", failureMessages);
    }

    @Named("mapFailureMessagesToList")
    default List<String> mapFailureMessagesToList(String failureMessages) {
        return failureMessages == null || failureMessages.isBlank()
                ? List.of()
                : Arrays.stream(failureMessages.split(",")).toList();
    }

}
