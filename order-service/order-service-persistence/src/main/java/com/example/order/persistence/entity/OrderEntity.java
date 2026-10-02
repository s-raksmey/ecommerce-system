package com.example.order.persistence.entity;

import com.example.valueobject.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class OrderEntity {
    @Id
    private UUID id;

    private UUID customerId;

    private UUID businessId;

    private BigDecimal price;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItemEntity> items;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "address_id")
    private OrderAddressEntity orderAddress;

    private UUID trackingId;

    @Enumerated(EnumType.ORDINAL)
    private OrderStatus orderStatus;

    private String failureMessages;

    private OrderEntity(Builder builder) {
        setId(builder.id);
        setCustomerId(builder.customerId);
        setBusinessId(builder.businessId);
        setPrice(builder.price);
        setItems(builder.items);
        setOrderAddress(builder.orderAddress);
        setTrackingId(builder.trackingId);
        setOrderStatus(builder.orderStatus);
        setFailureMessages(builder.failureMessages);
    }

    public static final class Builder {
        private UUID id;
        private UUID customerId;
        private UUID businessId;
        private BigDecimal price;
        private List<OrderItemEntity> items;
        private OrderAddressEntity orderAddress;
        private UUID trackingId;
        private OrderStatus orderStatus;
        private String failureMessages;

        public static Builder newBuilder() {
            return new Builder();
        }

        private Builder() {
        }

        public Builder id(UUID val) {
            id = val;
            return this;
        }

        public Builder customerId(UUID val) {
            customerId = val;
            return this;
        }

        public Builder businessId(UUID val) {
            businessId = val;
            return this;
        }

        public Builder price(BigDecimal val) {
            price = val;
            return this;
        }

        public Builder items(List<OrderItemEntity> val) {
            items = val;
            return this;
        }

        public Builder orderAddress(OrderAddressEntity val) {
            orderAddress = val;
            return this;
        }

        public Builder trackingId(UUID val) {
            trackingId = val;
            return this;
        }

        public Builder orderStatus(OrderStatus val) {
            orderStatus = val;
            return this;
        }

        public Builder failureMessages(String val) {
            failureMessages = val;
            return this;
        }

        public OrderEntity build() {
            return new OrderEntity(this);
        }
    }
}
