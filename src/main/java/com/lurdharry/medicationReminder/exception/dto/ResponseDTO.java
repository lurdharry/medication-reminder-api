package com.lurdharry.medicationReminder.exception.dto;

import lombok.Builder;

@Builder
public record ResponseDTO(
    Integer statusCode,
    String message
) {
}
