package com.lurdharry.medicationReminder.emergencycontact.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record EmergencyContactResponse(
        UUID id,
        String name,
        String relationship,
        String phone,
        String email,
        Boolean isPrimary,
        Boolean notifyOnMissedDose,
        LocalDateTime createdAt
) {}

