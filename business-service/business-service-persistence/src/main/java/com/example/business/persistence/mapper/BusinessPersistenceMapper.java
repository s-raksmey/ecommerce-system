package com.example.business.persistence.mapper;

import com.example.business.domain.entity.Business;
import com.example.business.domain.entity.OrderDetail;
import com.example.business.domain.entity.Product;
import com.example.business.persistence.entity.BusinessEntity;
import com.example.domain.valueobject.BusinessId;
import com.example.domain.valueobject.Money;
import com.example.domain.valueobject.ProductId;
import com.example.persistence.business.exception.BusinessPersistenceException;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessPersistenceMapper {

    default List<UUID> businessToBusinessProducts(Business business) {
        return business.getOrderDetail().getProducts().stream().map(product -> product.getId().value()).toList();
    }

    default Business businessEntityToBusiness(List<BusinessEntity> businessEntities) {
        BusinessEntity businessEntity = businessEntities.stream().findFirst().orElseThrow(() -> new BusinessPersistenceException("Business could not be found"));

        List<Product> products = businessEntities.stream().map(entity -> Product.builder()
                .id(new ProductId(entity.getProductId()))
                .name(entity.getProductName())
                .price(new Money(entity.getProductPrice()))
                .available(entity.getProductAvailable())
                .build()).toList();

        return Business.builder()
                .id(new BusinessId(businessEntity.getBusinessId()))
                .active(businessEntity.getBusinessActive())
                .orderDetail(OrderDetail.builder().products(products).build())
                .build();
    }

}
