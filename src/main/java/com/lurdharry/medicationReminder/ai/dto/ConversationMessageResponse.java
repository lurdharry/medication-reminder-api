package com.lurdharry.medicationReminder.ai.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ConversationMessageResponse(
        UUID id,
        String role,
        String content,
        String intent,
        LocalDateTime createdAt
) {}
