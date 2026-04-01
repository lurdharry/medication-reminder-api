package com.lurdharry.medicationReminder.doserecord.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lurdharry.medicationReminder.doserecord.model.DoseStatus;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DoseRecordResponse(
        UUID id,
        UUID doseScheduleId,
        UUID medicationId,
        UUID userId,
        LocalDateTime scheduledAt,
        DoseStatus status,
        LocalDateTime recordedAt,
        LocalDateTime createdAt
) {}
