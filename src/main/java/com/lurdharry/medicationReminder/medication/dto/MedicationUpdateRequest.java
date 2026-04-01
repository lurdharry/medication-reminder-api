package com.lurdharry.medicationReminder.medication.dto;

import com.lurdharry.medicationReminder.medication.model.MedicationUnit;


import java.time.LocalDate;

public record MedicationUpdateRequest(

        String name,

        String dosage,

        MedicationUnit unit,

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
