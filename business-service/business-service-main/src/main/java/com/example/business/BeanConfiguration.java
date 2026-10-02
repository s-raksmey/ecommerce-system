package com.example.business;

import com.example.business.domain.service.BusinessDomainService;
import com.example.business.domain.service.BusinessDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public BusinessDomainService businessDomainService() {
        return new BusinessDomainServiceImpl();
    }

}
