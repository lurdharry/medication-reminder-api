package com.lurdharry.medicationReminder.ai.dto;

import lombok.Builder;

@Builder
public record ChatResponse(
        String message,
        String provider
) {}
