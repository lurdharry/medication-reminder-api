package com.lurdharry.medicationReminder.medication;


import com.lurdharry.medicationReminder.medication.dto.MedicationRequest;
import com.lurdharry.medicationReminder.medication.dto.MedicationResponse;
import com.lurdharry.medicationReminder.medication.model.Medication;
import org.springframework.stereotype.Component;

@Component
public class MedicationMapper {

    public Medication toMedication(MedicationRequest request) {
        return Medication.builder()
                .name(request.name())
                .dosage(request.dosage())
                .unit(request.unit())
                .purpose(request.purpose())
                .instruction(request.instruction())
                .pharmacyInfo(request.pharmacyInfo())
                .imageUrl(request.imageUrl())
                .color(request.color())
                .shape(request.shape())
                .startDate(request.startDate())
                .endDate(request.endDate())
                .refillDate(request.refillDate())
                .prescribedBy(request.prescribedBy())
                .build();
    }

    public MedicationResponse toMedicationResponse(Medication medication) {
        return MedicationResponse.builder()
                .id(medication.getId())
                .name(medication.getName())
                .userId(medication.getUser().getId())
                .dosage(medication.getDosage())
                .purpose(medication.getPurpose())
                .instruction(medication.getInstruction())
                .createdAt(medication.getCreatedAt())
                .updatedAt(medication.getUpdatedAt())
                .color(medication.getColor())
                .shape(medication.getShape())
                .unit(medication.getUnit())
                .prescribedBy(medication.getPrescribedBy())
                .refillDate(medication.getRefillDate())
                .startDate(medication.getStartDate())
                .active(medication.getActive())
                .endDate(medication.getEndDate())
                .imageUrl(medication.getImageUrl())
                .pharmacyInfo(medication.getPharmacyInfo())
                .build();
    }

  public void  updateMedicationFromRequest(MedicationRequest request, Medication medication) {
      if (request.name() != null) medication.setName(request.name());
      if (request.dosage() != null) medication.setDosage(request.dosage());
      if (request.unit() != null) medication.setUnit(request.unit());
      if (request.purpose() != null) medication.setPurpose(request.purpose());
      if (request.instruction() != null) medication.setInstruction(request.instruction());
      if (request.pharmacyInfo() != null) medication.setPharmacyInfo(request.pharmacyInfo());
      if (request.imageUrl() != null) medication.setImageUrl(request.imageUrl());
      if (request.color() != null) medication.setColor(request.color());
      if (request.shape() != null) medication.setShape(request.shape());
      if (request.startDate() != null) medication.setStartDate(request.startDate());
      if (request.endDate() != null) medication.setEndDate(request.endDate());
      if (request.refillDate() != null) medication.setRefillDate(request.refillDate());
      if (request.prescribedBy() != null) medication.setPrescribedBy(request.prescribedBy());
  }
}
