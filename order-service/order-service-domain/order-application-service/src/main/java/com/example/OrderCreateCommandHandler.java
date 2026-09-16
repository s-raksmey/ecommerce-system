package com.example;

import com.example.dto.CreateOrderRequest;
import com.example.dto.CreateOrderResponse;
import com.example.entity.Business;
import com.example.entity.Order;
import com.example.mapper.OrderApplicationMapper;
import com.example.port.input.CreateOrderUseCase;
import com.example.port.ouput.OrderRepository;
import com.example.service.OrderDomainService;

public class OrderCreateCommandHandler implements CreateOrderUseCase {

    private final OrderDomainService orderDomainService;
    private final OrderRepository orderRepository;
    private final OrderApplicationMapper orderApplicationMapper;

    public OrderCreateCommandHandler(
            OrderDomainService orderDomainService,
            OrderRepository orderRepository,
            OrderApplicationMapper orderApplicationMapper
    ) {
        this.orderDomainService = orderDomainService;
        this.orderRepository = orderRepository;
        this.orderApplicationMapper = orderApplicationMapper;
    }

    @Override
    public CreateOrderResponse execute(CreateOrderRequest createOrderRequest) {
        Order order = orderApplicationMapper.createOrderRequestToOrder(createOrderRequest);
        Business business = orderApplicationMapper.createOrderRequestToBusiness(createOrderRequest);

        orderDomainService.validateAndInitiateOrder(order, business);

        Order savedOrder = orderRepository.saveOrder(order);

        return new CreateOrderResponse(savedOrder.getId());
    }

}
