package com.example.order.persistence.mapper;

import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderItem;
import com.example.order.domain.entity.Product;
import com.example.order.persistence.entity.OrderAddressEntity;
import com.example.order.persistence.entity.OrderEntity;
import com.example.order.persistence.entity.OrderItemEntity;
import com.example.valueobject.Money;
import com.example.valueobject.ProductId;
import com.example.valueobject.StreetAddress;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "businessId.value", target = "businessId")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "deliveryAddress", target = "orderAddress")
    @Mapping(source = "trackingId.value", target = "trackingId")
    @Mapping(source = "failureMessages", target = "failureMessages", qualifiedByName = "mapFailureMessages")
    OrderEntity orderToOrderEntity(Order order);

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "product.id.value", target = "productId")
    @Mapping(source = "price.amount", target = "price")
    @Mapping(source = "subTotal.amount", target = "subTotal")
    @Mapping(target = "order", ignore = true)
    OrderItemEntity orderItemToOrderItemEntity(OrderItem orderItem);

    @Mapping(target = "order", ignore = true)
    OrderAddressEntity streetAddressToOrderAddressEntity(StreetAddress streetAddress);

    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "customerId.value", source = "customerId")
    @Mapping(target = "businessId.value", source = "businessId")
    @Mapping(target = "price.amount", source = "price")
    @Mapping(target = "deliveryAddress", source = "orderAddress")
    @Mapping(target = "trackingId.value", source = "trackingId")
    @Mapping(target = "failureMessages", source = "failureMessages", qualifiedByName = "mapFailureMessagesToList")
    Order orderEntityToOrder(OrderEntity orderEntity);

    @Mapping(target = "id.value", source = "id")
    @Mapping(target = "product", source = ".", qualifiedByName = "orderItemEntityToProduct")
    @Mapping(target = "price.amount", source = "price")
    @Mapping(target = "subTotal.amount", source = "subTotal")
    @Mapping(target = "orderId", ignore = true)
    OrderItem orderItemEntityToOrderItem(OrderItemEntity orderItemEntity);

    StreetAddress orderAddressEntityToStreetAddress(OrderAddressEntity orderAddressEntity);

    @Named("mapFailureMessages")
    default String mapFailureMessages(List<String> failureMessages) {
        if (failureMessages == null || failureMessages.isEmpty()) {
            return null;
        }
        return String.join(",", failureMessages);
    }

    @Named("mapFailureMessagesToList")
    default List<String> mapFailureMessagesToList(String failureMessages) {
        if (failureMessages == null || failureMessages.isBlank()) {
            return List.of();
        }
        return Arrays.stream(failureMessages.split(",")).toList();
    }

    @Named("orderItemEntityToProduct")
    default Product orderItemEntityToProduct(OrderItemEntity orderItemEntity) {
        return Product.builder()
                .id(new ProductId(orderItemEntity.getProductId()))
                .name("product")
                .price(new Money(orderItemEntity.getPrice()))
                .build();
    }

    @AfterMapping
    default void afterOrderToOrderEntity(Order order, @MappingTarget OrderEntity orderEntity) {
        if (orderEntity.getItems() != null) {
            orderEntity.getItems().forEach(item -> item.setOrder(orderEntity));
        }
        if (orderEntity.getOrderAddress() != null) {
            orderEntity.getOrderAddress().setOrder(orderEntity);
        }
    }

}
