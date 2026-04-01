package com.lurdharry.medicationReminder.doserecord.dto;

import com.lurdharry.medicationReminder.doserecord.model.DoseStatus;
import jakarta.validation.constraints.NotNull;

public record DoseRecordRequest(
        @NotNull(message = "status cannot be null")
        DoseStatus status
) {}