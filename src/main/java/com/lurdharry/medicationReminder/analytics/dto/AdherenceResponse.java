package com.lurdharry.medicationReminder.analytics.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record AdherenceResponse(
        int totalDoses,
        int taken,
        int skipped,
        int missed,
        double adherenceRate,
        int currentStreak,
        List<MedicationAdherence> perMedication
) {}
