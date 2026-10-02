package com.example.order.domain.port.ouput;

import com.example.order.domain.entity.Business;

import java.util.Optional;

public interface BusinessRepository {

    Optional<Business> findBusiness(Business business);

}
