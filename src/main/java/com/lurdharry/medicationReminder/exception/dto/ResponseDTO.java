package com.lurdharry.medicationReminder.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record ResponseDTO(
    Integer statusCode,
    String message,
    Object data
) {
}
