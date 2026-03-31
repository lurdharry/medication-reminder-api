package com.lurdharry.medicationReminder.medication.dto;

import com.lurdharry.medicationReminder.medication.model.MedicationUnit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MedicationRequest(
        @NotBlank(message = "name cannot empty")
        String name,

        @NotBlank(message = "dosage cannot be empty")
        String dosage,

        @NotNull(message = "unit cannot be empty")
        MedicationUnit unit,

        @NotBlank(message = "purpose cannot be empty")
        String purpose,

        String instruction,

        String pharmacyInfo,

        String imageUrl,

        String color,

        String shape,

        Boolean active,

        LocalDate startDate,

        LocalDate endDate,

        LocalDate refillDate,

        String prescribedBy
) {

}
