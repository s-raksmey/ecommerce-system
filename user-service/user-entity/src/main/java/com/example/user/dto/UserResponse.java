package com.example.user.dto;

import lombok.Builder;

import java.util.Date;

@Builder
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
