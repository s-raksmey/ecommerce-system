package com.example.order;

import com.example.order.domain.service.OrderDomainService;
import com.example.order.domain.service.OrderDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    /**
     * Wires the domain service from the composition root.
     * {@link OrderDomainServiceImpl} stays free of Spring so the domain core does not depend on the framework.
     */
    @Bean
    public OrderDomainService orderDomainService() {
        return new OrderDomainServiceImpl();
    }

}
