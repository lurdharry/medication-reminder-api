package com.lurdharry.medicationReminder.auth.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record LoginResponse(
        UUID userId,
        String email,
        String name,
        String accessToken,
        String refreshToken
) {
}
