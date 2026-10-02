package com.example.business.domain.service;

import com.example.business.domain.entity.Business;

public class BusinessDomainServiceImpl implements BusinessDomainService {
    @Override
    public void validateAndInitializeBusiness(Business business) {
        business.validateBusiness();
        business.initializeBusiness();
    }
}
