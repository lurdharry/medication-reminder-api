package com.lurdharry.medicationReminder.doseschedule;

import com.lurdharry.medicationReminder.doseschedule.model.DoseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface DoseScheduleRepository extends JpaRepository<DoseSchedule, UUID> {
    List<DoseSchedule> findByMedicationId(UUID medicationId);
}
