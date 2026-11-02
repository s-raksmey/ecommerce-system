package com.example.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;

import java.util.Date;

public record UserUpdateRequest(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String familyName,
        @NotBlank
        String givenName,
        String gender,
        @Past
        Date birthday,
        String mobilePhone,
        String address
) {
}
