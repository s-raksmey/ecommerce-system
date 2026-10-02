package com.example.order.domain.entity;

import com.example.entity.BaseEntity;
import com.example.valueobject.Money;
import com.example.valueobject.ProductId;

public class Product extends BaseEntity<ProductId> {
    private String name;
    private Money price;

    public String getName() {
        return name;
    }

    public Money getPrice() {
        return price;
    }

    public void updateWithConfirmedNameAndPrice(String name, Money price) {
        this.name = name;
        this.price = price;
    }

    private Product(Builder builder) {
        super.setId(builder.id);
        name = builder.name;
        price = builder.price;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private ProductId id;
        private String name;
        private Money price;

        private Builder() {
        }

        public Builder id(ProductId val) {
            id = val;
            return this;
        }

        public Builder name(String val) {
            name = val;
            return this;
        }

        public Builder price(Money val) {
            price = val;
            return this;
        }

        public Product build() {
            return new Product(this);
        }
    }
}
