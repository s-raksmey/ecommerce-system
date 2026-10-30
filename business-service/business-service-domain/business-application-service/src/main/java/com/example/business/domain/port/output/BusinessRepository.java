package com.example.business.domain.port.output;

import com.example.business.domain.entity.Business;

import java.util.Optional;

public interface BusinessRepository {

    Optional<Business> findBusinessInformation(Business business);

}
