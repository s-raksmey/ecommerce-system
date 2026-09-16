package com.example.order.config;

import com.example.OrderCreateCommandHandler;
import com.example.mapper.OrderApplicationMapper;
import com.example.port.input.CreateOrderUseCase;
import com.example.port.ouput.OrderRepository;
import com.example.service.OrderDomainService;
import com.example.service.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

    @Bean
    public OrderApplicationMapper orderApplicationMapper() {
        return new OrderApplicationMapper();
    }

    @Bean
    public CreateOrderUseCase createOrderUseCase(
            OrderDomainService orderDomainService,
            OrderRepository orderRepository,
            OrderApplicationMapper orderApplicationMapper
    ) {
        return new OrderCreateCommandHandler(
                orderDomainService,
                orderRepository,
                orderApplicationMapper
        );
    }

}
