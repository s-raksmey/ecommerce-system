package com.example.order.persistence.mapper;

import com.example.order.domain.entity.Business;
import com.example.order.domain.entity.Customer;
import com.example.order.domain.entity.Product;
import com.example.order.persistence.entity.BusinessEntity;
import com.example.order.persistence.entity.CustomerEntity;
import com.example.valueobject.BusinessId;
import com.example.valueobject.Money;
import com.example.valueobject.ProductId;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderPersistenceMapper {

    @Mapping(source = "id", target = "id.value")
    Customer customerEntityToCustomer(CustomerEntity customerEntity);

    @Mapping(source = "businessId", target = "id.value")
    Business businessEntityToBusiness(BusinessEntity businessEntity);

}
