package com.example.business.persistence.adapter;

import com.example.business.domain.entity.OrderApproval;
import com.example.business.domain.port.output.OrderApprovalRepository;
import com.example.business.persistence.entity.OrderApprovalEntity;
import com.example.business.persistence.mapper.OrderApprovalPersistenceMapper;
import com.example.business.persistence.repository.OrderApprovalJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class OrderApprovalRepositoryAdapter implements OrderApprovalRepository {

    private final OrderApprovalJpaRepository orderApprovalJpaRepository;
    private final OrderApprovalPersistenceMapper orderApprovalPersistenceMapper;

    @Override
    public OrderApproval save(OrderApproval orderApproval) {
        OrderApprovalEntity orderApprovalEntity = orderApprovalPersistenceMapper.orderApprovalToOrderApprovalEntity(orderApproval);

        return orderApprovalPersistenceMapper.orderApprovalEntityToOrderApproval(orderApprovalJpaRepository.save(orderApprovalEntity));
    }
}
