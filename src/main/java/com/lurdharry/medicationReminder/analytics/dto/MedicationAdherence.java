package com.lurdharry.medicationReminder.analytics.dto;

import lombok.Builder;

import java.util.UUID;

@Builder
public record MedicationAdherence(
        UUID medicationId,
        String medicationName,
        int totalDoses,
        int taken,
        int skipped,
        int missed,
        double adherenceRate
) {}
