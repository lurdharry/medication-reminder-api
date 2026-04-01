package com.lurdharry.medicationReminder.ai.dto;

import lombok.Builder;

import java.util.Map;

@Builder
public record ChatResponse(
        String message,
        AIIntent intent,
        Map<String, Object> data,
        String provider
) {}
