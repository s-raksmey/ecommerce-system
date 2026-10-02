package com.example.business.restapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductRequest(
        @NotBlank
        String name,
        @NotNull
        BigDecimal price
) {
}
