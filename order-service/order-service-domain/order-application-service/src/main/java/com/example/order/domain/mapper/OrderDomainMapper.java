package com.example.order.domain.mapper;

import com.example.order.domain.dto.CommandOrderAddress;
import com.example.order.domain.dto.CommandOrderItem;
import com.example.order.domain.dto.CreateOrderCommand;
import com.example.order.domain.entity.Order;
import com.example.order.domain.entity.OrderItem;
import com.example.valueobject.StreetAddress;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface OrderDomainMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trackingId", ignore = true)
    @Mapping(target = "orderStatus", ignore = true)
    @Mapping(target = "failureMessages", ignore = true)
    @Mapping(source = "customerId", target = "customerId.value")
    @Mapping(source = "businessId", target = "businessId.value")
    @Mapping(source = "price", target = "price.amount")
    @Mapping(source = "deliveryAddress", target = "deliveryAddress")
    @Mapping(source = "items", target = "items")
    Order createOrderCommandToOrder(CreateOrderCommand createOrderCommand);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "orderId", ignore = true)
    @Mapping(source = "productId", target = "product.id.value")
    @Mapping(target = "product.name", constant = "product")
    @Mapping(source = "price", target = "product.price.amount")
    @Mapping(source = "price", target = "price.amount")
    @Mapping(source = "subTotal", target = "subTotal.amount")
    OrderItem commandOrderItemToOrderItem(CommandOrderItem commandOrderItem);

    default StreetAddress map(CommandOrderAddress commandOrderAddress) {
        if (commandOrderAddress == null) {
            return null;
        }
        return new StreetAddress(
                UUID.randomUUID(),
                commandOrderAddress.street(),
                commandOrderAddress.postalCode(),
                commandOrderAddress.city()
        );
    }

}
