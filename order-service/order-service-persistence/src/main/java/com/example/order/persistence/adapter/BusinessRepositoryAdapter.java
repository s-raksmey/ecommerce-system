package com.example.order.persistence.adapter;

import com.example.order.domain.entity.Business;
import com.example.order.domain.port.ouput.BusinessRepository;
import com.example.order.persistence.mapper.OrderPersistenceMapper;
import com.example.order.persistence.repository.BusinessJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class BusinessRepositoryAdapter implements BusinessRepository {

    private final BusinessJpaRepository businessJpaRepository;
    private final OrderPersistenceMapper orderPersistenceMapper;

    @Override
    public Optional<Business> findBusiness(UUID businessId) {
        return businessJpaRepository.findById(businessId).map(orderPersistenceMapper::businessEntityToBusiness);
    }
}
