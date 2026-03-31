package com.lurdharry.medicationReminder.medication.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.lurdharry.medicationReminder.medication.model.MedicationUnit;
import lombok.Builder;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record MedicationResponse(
        UUID id,
        String name,

        String dosage,
        MedicationUnit unit,

        String purpose,

        UUID userId,

         String instruction,

         String pharmacyInfo,

         String imageUrl,

         String color,

         String shape,

         Boolean active,

         LocalDate startDate,

         LocalDate endDate,

         LocalDate refillDate,

         String prescribedBy,

        LocalDateTime createdAt,
        LocalDateTime updatedAt

) {


}
