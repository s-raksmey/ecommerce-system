package com.example.business.persistence.adapter;

import com.example.business.domain.entity.Business;
import com.example.business.domain.port.output.BusinessRepository;
import com.example.business.persistence.entity.BusinessEntity;
import com.example.business.persistence.mapper.BusinessPersistenceMapper;
import com.example.business.persistence.repository.BusinessJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final BusinessJpaRepository businessJpaRepository;
    private final BusinessPersistenceMapper businessPersistenceMapper;

    @Override
    public Business saveBusiness(Business business) {
        BusinessEntity businessEntity = businessPersistenceMapper.businessToBusinessEntity(business);
        BusinessEntity savedBusinessEntity = businessJpaRepository.save(businessEntity);
        return businessPersistenceMapper.businessEntityToBusiness(savedBusinessEntity);
    }
}
