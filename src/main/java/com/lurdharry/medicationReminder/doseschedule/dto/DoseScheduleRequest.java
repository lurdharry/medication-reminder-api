package com.lurdharry.medicationReminder.doseschedule.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalTime;

public record DoseScheduleRequest(
        @NotNull(message = "time cannot be null")
        LocalTime time
) {}
