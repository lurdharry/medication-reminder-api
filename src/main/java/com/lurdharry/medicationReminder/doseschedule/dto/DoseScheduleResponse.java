package com.lurdharry.medicationReminder.doseschedule.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record DoseScheduleResponse(
        UUID id,
        UUID medicationId,
        LocalTime time,
        LocalDate createdAt
) {}
