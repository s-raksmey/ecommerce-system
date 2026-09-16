package com.example.order.restapi.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record OrderCreateResponse(
        UUID orderId
) {
}
