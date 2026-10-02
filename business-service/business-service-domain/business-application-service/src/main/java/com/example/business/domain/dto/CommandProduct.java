package com.example.business.domain.dto;

import java.math.BigDecimal;

public record CommandProduct(
        String name,
        BigDecimal price
) {
}
