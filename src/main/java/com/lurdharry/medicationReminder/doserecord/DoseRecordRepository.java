package com.lurdharry.medicationReminder.doserecord;

import com.lurdharry.medicationReminder.doserecord.model.DoseRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoseRecordRepository extends JpaRepository<DoseRecord, UUID> {
    List<DoseRecord> findByUserId(UUID userId);
    List<DoseRecord> findByDoseScheduleId(UUID scheduleId);
    List<DoseRecord> findByMedicationId(UUID medicationId);
}