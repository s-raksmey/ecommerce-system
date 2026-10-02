package com.example.order.config;

import com.example.order.persistence.entity.BusinessEntity;
import com.example.order.persistence.entity.CustomerEntity;
import com.example.order.persistence.repository.BusinessJpaRepository;
import com.example.order.persistence.repository.CustomerJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderDataSeeder implements CommandLineRunner {

    public static final UUID CUSTOMER_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");
    public static final UUID BUSINESS_ID = UUID.fromString("22222222-2222-2222-2222-222222222222");
    public static final UUID PRODUCT_ID = UUID.fromString("33333333-3333-3333-3333-333333333333");

    private final CustomerJpaRepository customerJpaRepository;
    private final BusinessJpaRepository businessJpaRepository;

    @Override
    public void run(String... args) {
        seedCustomer();
        seedBusiness();
        log.info("Demo data ready: customerId={}, businessId={}, productId={}",
                CUSTOMER_ID, BUSINESS_ID, PRODUCT_ID);
    }

    private void seedCustomer() {
        if (customerJpaRepository.existsById(CUSTOMER_ID)) {
            return;
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setId(CUSTOMER_ID);
        customer.setUsername("demo_user");
        customer.setFamilyName("Demo");
        customer.setGivenName("Customer");
        customerJpaRepository.save(customer);
    }

    private void seedBusiness() {
        boolean alreadyExists = businessJpaRepository.findByBusinessIdAndProductIdIn(
                BUSINESS_ID,
                List.of(PRODUCT_ID)
        ).stream().findAny().isPresent();

        if (alreadyExists) {
            return;
        }

        BusinessEntity business = new BusinessEntity();
        business.setBusinessId(BUSINESS_ID);
        business.setProductId(PRODUCT_ID);
        business.setBusinessActive(true);
        business.setProductName("Demo Product");
        business.setProductPrice(new BigDecimal("10.00"));
        businessJpaRepository.save(business);
    }

}
