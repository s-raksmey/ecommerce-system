package com.example.order.domain.port.ouput;

import com.example.order.domain.entity.Business;

import java.util.Optional;
import java.util.UUID;

public interface BusinessRepository {

    Optional<Business> findBusiness(UUID businessId);

}
