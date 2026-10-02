package com.example.customer;

import com.example.customer.domain.service.CustomerDomainService;
import com.example.customer.domain.service.CustomerDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public CustomerDomainService customerDomainService() {
        return new CustomerDomainServiceImpl();
    }

}
