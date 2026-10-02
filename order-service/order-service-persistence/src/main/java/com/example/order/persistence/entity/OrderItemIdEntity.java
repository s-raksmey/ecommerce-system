package com.example.order.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemIdEntity implements Serializable {
    private Integer id;
    private OrderEntity order;
}
