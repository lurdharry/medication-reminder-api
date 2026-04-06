package com.lurdharry.medicationReminder.analytics.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record PatternResponse(
        String bestTimeOfDay,
        String worstTimeOfDay,
        String bestDayOfWeek,
        String adherenceTrend,
        List<String> suggestions
) {}

