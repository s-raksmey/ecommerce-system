package com.example.order.domain.port.ouput;

import com.example.order.domain.entity.Business;

import java.util.Optional;

/**
 * Output port used by the create-order use case to load the business and the
 * products on the order. {@code BusinessRepositoryAdapter} is the driven adapter.
 */
public interface BusinessRepository {

    Optional<Business> findBusiness(Business business);

}
