package com.example.payment.persistence.credithistory.mapper;

import com.example.payment.domain.entity.CreditHistory;
import com.example.payment.persistence.credithistory.entity.CreditHistoryEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CreditHistoryPersistenceMapper {

    @Mapping(source = "id.value", target = "id")
    @Mapping(source = "customerId.value", target = "customerId")
    @Mapping(source = "amount.amount", target = "amount")
    CreditHistoryEntity creditHistoryToCreditHistoryEntity(CreditHistory creditHistory);

    @Mapping(target = "creditHistoryId.value", source = "id")
    @Mapping(target = "customerId.value", source = "customerId")
    @Mapping(target = "amount.amount", source = "amount")
    CreditHistory creditHistoryEntityToCreditHistory(CreditHistoryEntity creditHistoryEntity);

}
