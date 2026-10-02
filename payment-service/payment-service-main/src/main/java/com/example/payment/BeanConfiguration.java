package com.example.payment;

import com.example.payment.domain.service.PaymentDomainService;
import com.example.payment.domain.service.PaymentDomainServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public PaymentDomainService paymentDomainService() {
        return new PaymentDomainServiceImpl();
    }

}
