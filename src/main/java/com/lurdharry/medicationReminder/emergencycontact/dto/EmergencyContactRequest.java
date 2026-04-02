package com.lurdharry.medicationReminder.emergencycontact.dto;

import jakarta.validation.constraints.NotBlank;

public record EmergencyContactRequest(
        @NotBlank(message = "Name cannot be empty")
        String name,

        @NotBlank(message = "Relationship cannot be empty")
        String relationship,

        String phone,

        String email,

        Boolean isPrimary,

        Boolean notifyOnMissedDose
) {}

