package com.example.order.restapi.dto;

import lombok.Builder;

import java.util.UUID;

/**
 * HTTP response for a created order. Holds only the id returned by the use case.
 */
@Builder
public record OrderCreateResponse(
        UUID orderId
) {
}
