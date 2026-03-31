package com.lurdharry.medicationReminder.medication;

import com.lurdharry.medicationReminder.medication.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface MedicationRepository extends JpaRepository<Medication, UUID> {

    List<Medication> findByUserId(UUID userId);
}
