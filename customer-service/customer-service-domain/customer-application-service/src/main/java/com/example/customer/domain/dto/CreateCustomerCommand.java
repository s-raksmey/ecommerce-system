package com.example.customer.domain.dto;

public record CreateCustomerCommand(
        String username,
        String familyName,
        String givenName
) {
}
