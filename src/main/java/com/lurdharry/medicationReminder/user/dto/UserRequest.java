package com.lurdharry.medicationReminder.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @Email(message = "Must be a valid email")
        @NotBlank(message =  "email cannot be empty")
        String email,

        @NotBlank(message = "Name cannot be blank")
        String name,

        @NotBlank(message =  "Password cannot be empty")
        String password
) {
}
