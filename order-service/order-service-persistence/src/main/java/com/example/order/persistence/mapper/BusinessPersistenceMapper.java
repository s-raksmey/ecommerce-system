package com.example.order.persistence.mapper;

import com.example.order.domain.entity.Business;
import com.example.order.domain.entity.Product;
import com.example.order.persistence.entity.BusinessEntity;
import com.example.order.persistence.exception.BusinessPersistenceException;
import com.example.valueobject.BusinessId;
import com.example.valueobject.Money;
import com.example.valueobject.ProductId;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface BusinessPersistenceMapper {

    default List<UUID> businessToBusinessProducts(Business business) {
        return business.getProducts().stream()
                .map(product -> product.getId().value())
                .toList();
    }

    default Business businessEntityToBusiness(List<BusinessEntity> businessEntities) {
        BusinessEntity businessEntity = businessEntities.stream()
                .findFirst()
                .orElseThrow(() -> new BusinessPersistenceException("Business could not be found"));

        List<Product> businessProducts = businessEntities.stream()
                .map(entity -> Product.builder()
                        .id(new ProductId(entity.getProductId()))
                        .name(entity.getProductName())
                        .price(new Money(entity.getProductPrice()))
                        .build())
                .toList();

        return Business.builder()
                .id(new BusinessId(businessEntity.getBusinessId()))
                .products(businessProducts)
                .active(Boolean.TRUE.equals(businessEntity.getBusinessActive()))
                .build();
    }

}
