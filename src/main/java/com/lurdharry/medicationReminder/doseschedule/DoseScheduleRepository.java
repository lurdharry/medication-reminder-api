package com.lurdharry.medicationReminder.doseschedule;

import com.lurdharry.medicationReminder.doseschedule.model.DoseSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface DoseScheduleRepository extends JpaRepository<DoseSchedule, UUID> {
    List<DoseSchedule> findByMedicationId(UUID medicationId);

    @Query("""
    SELECT ds FROM DoseSchedule ds
    JOIN FETCH ds.medication m
    JOIN FETCH m.user u
    WHERE m.active = true
    AND ds.time <= :now
    """)
    List<DoseSchedule> findOverdueSchedules(@Param("now") LocalTime now);
}
