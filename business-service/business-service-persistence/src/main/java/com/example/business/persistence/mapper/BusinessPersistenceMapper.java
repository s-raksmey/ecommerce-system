package com.example.business.persistence.mapper;

import com.example.business.domain.entity.Business;
import com.example.business.domain.entity.Product;
import com.example.business.persistence.entity.BusinessEntity;
import com.example.business.persistence.entity.ProductEntity;
import com.example.valueobject.BusinessId;
import com.example.valueobject.Money;
import com.example.valueobject.ProductId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BusinessPersistenceMapper {

    public BusinessEntity businessToBusinessEntity(Business business) {
        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setId(business.getId().value());
        businessEntity.setActive(business.isActive());

        List<ProductEntity> productEntities = business.getProducts().stream()
                .map(product -> {
                    ProductEntity productEntity = new ProductEntity();
                    productEntity.setId(product.getId().value());
                    productEntity.setName(product.getName());
                    productEntity.setPrice(product.getPrice().getAmount());
                    productEntity.setBusiness(businessEntity);
                    return productEntity;
                })
                .toList();
        businessEntity.setProducts(new ArrayList<>(productEntities));
        return businessEntity;
    }

    public Business businessEntityToBusiness(BusinessEntity businessEntity) {
        List<Product> products = businessEntity.getProducts().stream()
                .map(productEntity -> Product.builder()
                        .id(new ProductId(productEntity.getId()))
                        .name(productEntity.getName())
                        .price(new Money(productEntity.getPrice()))
                        .build())
                .toList();

        return Business.builder()
                .id(new BusinessId(businessEntity.getId()))
                .active(Boolean.TRUE.equals(businessEntity.getActive()))
                .products(products)
                .build();
    }
}
