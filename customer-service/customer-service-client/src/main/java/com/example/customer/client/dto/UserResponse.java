package com.example.customer.client.dto;

import java.util.Date;

/**
 * User data returned by user-service.
 *
 * <p>This contract intentionally mirrors user-service's public response instead
 * of depending on its internal user-entity module.</p>
 */
public record UserResponse(
        String id,
        String familyName,
        String givenName,
        String gender,
        Date birthday,
        String mobilePhone,
        String address
) {
}
