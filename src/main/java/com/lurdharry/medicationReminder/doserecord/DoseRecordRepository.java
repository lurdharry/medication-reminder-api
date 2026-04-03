package com.lurdharry.medicationReminder.doserecord;

import com.lurdharry.medicationReminder.doserecord.dto.MedicationStatusCount;
import com.lurdharry.medicationReminder.doserecord.dto.StatusCount;
import com.lurdharry.medicationReminder.doserecord.model.DoseRecord;
import com.lurdharry.medicationReminder.doserecord.model.DoseStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface DoseRecordRepository extends JpaRepository<DoseRecord, UUID> {
    List<DoseRecord> findByUserId(UUID userId);

    List<DoseRecord> findByMedicationId(UUID medicationId);

    Long countByMedicationIdAndStatusAndScheduledAtAfter(UUID medicationId, DoseStatus status, LocalDateTime after);


    @Query("""
    SELECT dr.doseSchedule.id FROM DoseRecord dr
    WHERE dr.doseSchedule.id IN :scheduleIds
    AND CAST(dr.scheduledAt AS LocalDate) = :date
    """)
    Set<UUID> findExistingScheduleIdsForDate(@Param("scheduleIds") List<UUID> scheduleIds, @Param("date") LocalDate date);

    @Query(value = """
    SELECT status, COUNT(*) as count
    FROM dose_records
    WHERE user_id = :userId AND scheduled_at > :since
    GROUP BY status
    """, nativeQuery = true)
    List<StatusCount> countByStatus(@Param("userId") UUID userId, @Param("since") LocalDateTime since);

    @Query(value = """
    SELECT medication_id, status, COUNT(*) as count
    FROM dose_records
    WHERE user_id = :userId AND scheduled_at > :since
    GROUP BY medication_id, status
    """, nativeQuery = true)
    List<MedicationStatusCount> countByMedicationAndStatus(@Param("userId") UUID userId, @Param("since") LocalDateTime since);

    @Query(value = """
    SELECT CAST(scheduled_at AS DATE) as dose_date
    FROM dose_records
    WHERE user_id = :userId
    GROUP BY CAST(scheduled_at AS DATE)
    HAVING COUNT(*) = SUM(CASE WHEN status = 'taken' THEN 1 ELSE 0 END)
    ORDER BY dose_date DESC
    """, nativeQuery = true)
    List<LocalDate> findCompletedDates(@Param("userId") UUID userId);

    Optional<DoseRecord> findByDoseScheduleIdAndScheduledAt(UUID doseScheduleId, LocalDateTime scheduledAt);

    List<DoseRecord> findTop5ByMedicationIdOrderByScheduledAtDesc(UUID medicationId);

}