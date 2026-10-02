package com.example.business.domain.entity;

import com.example.business.domain.exception.BusinessDomainException;
import com.example.entity.AggregateRoot;
import com.example.valueobject.BusinessId;
import com.example.valueobject.ProductId;

import java.util.List;
import java.util.UUID;

public class Business extends AggregateRoot<BusinessId> {
    private final boolean active;
    private final List<Product> products;

    private Business(Builder builder) {
        setId(builder.id);
        active = builder.active;
        products = builder.products;
    }

    public void validateBusiness() {
        if (products == null || products.isEmpty()) {
            throw new BusinessDomainException("Business must contain at least one product");
        }
        for (Product product : products) {
            if (product.getName() == null || product.getName().isBlank()) {
                throw new BusinessDomainException("Product name is required");
            }
            if (product.getPrice() == null || !product.getPrice().isGreaterThanZero()) {
                throw new BusinessDomainException("Product price must be greater than zero");
            }
        }
    }

    public void initializeBusiness() {
        setId(new BusinessId(UUID.randomUUID()));
        for (Product product : products) {
            product.setId(new ProductId(UUID.randomUUID()));
        }
    }

    public boolean isActive() {
        return active;
    }

    public List<Product> getProducts() {
        return products;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private BusinessId id;
        private boolean active;
        private List<Product> products;

        private Builder() {
        }

        public Builder id(BusinessId val) {
            id = val;
            return this;
        }

        public Builder active(boolean val) {
            active = val;
            return this;
        }

        public Builder products(List<Product> val) {
            products = val;
            return this;
        }

        public Business build() {
            return new Business(this);
        }
    }
}
