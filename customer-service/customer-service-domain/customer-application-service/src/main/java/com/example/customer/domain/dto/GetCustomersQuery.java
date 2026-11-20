package com.example.customer.domain.dto;

public record GetCustomersQuery(
        int page,
        int size
) {
}
