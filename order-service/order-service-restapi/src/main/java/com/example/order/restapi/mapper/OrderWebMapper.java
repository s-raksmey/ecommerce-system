package com.example.order.restapi.mapper;

import com.example.order.domain.dto.CreateOrderCommand;
import com.example.order.domain.dto.CreateOrderResult;
import com.example.order.restapi.dto.OrderCreateRequest;
import com.example.order.restapi.dto.OrderCreateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Boundary mapper for the create-order driving adapter.
 * Request DTO to {@link CreateOrderCommand}, and {@link CreateOrderResult} to the response DTO.
 */
@Mapper(componentModel = "spring")
public interface OrderWebMapper {

    @Mapping(source = "orderAddress", target = "deliveryAddress")
    CreateOrderCommand orderCreateRequestToCreateOrderCommand(
            OrderCreateRequest orderCreateRequest
    );

    OrderCreateResponse createOrderResultToOrderCreateResponse(
            CreateOrderResult createOrderResult
    );

}
