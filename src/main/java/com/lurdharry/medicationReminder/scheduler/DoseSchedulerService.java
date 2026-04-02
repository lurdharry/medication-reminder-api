package com.lurdharry.medicationReminder.scheduler;

import com.lurdharry.medicationReminder.alert.AlertService;
import com.lurdharry.medicationReminder.doserecord.DoseRecordRepository;
import com.lurdharry.medicationReminder.doserecord.model.DoseRecord;
import com.lurdharry.medicationReminder.doserecord.model.DoseStatus;
import com.lurdharry.medicationReminder.doseschedule.DoseScheduleRepository;
import com.lurdharry.medicationReminder.doseschedule.model.DoseSchedule;
import com.lurdharry.medicationReminder.medication.MedicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class DoseSchedulerService {

    private final DoseScheduleRepository scheduleRepository;
    private final DoseRecordRepository recordRepository;
    private final MedicationRepository medicationRepository;
    private final AlertService alertService;

    @Scheduled(fixedRate = 60000)
    public void checkMissedDoses() {
        System.out.println("Scheduler running at: " + LocalTime.now());
        var now = LocalTime.now();
        var today = LocalDate.now();

        var overdueSchedules = scheduleRepository.findOverdueSchedules(now);

        if (overdueSchedules.isEmpty()) return;

        // Get all schedule IDs
        var scheduleIds = overdueSchedules.stream()
                .map(DoseSchedule::getId)
                .toList();

        // Batch check: which ones already have records today
        var existingRecords = recordRepository
                .findExistingScheduleIdsForDate(scheduleIds, today);

        for (var schedule : overdueSchedules) {
            if (existingRecords.contains(schedule.getId())) continue;

            var medication = schedule.getMedication();
            var scheduledAt = LocalDateTime.of(today, schedule.getTime());

            recordRepository.save(DoseRecord.builder()
                    .doseSchedule(schedule)
                    .medication(medication)
                    .user(medication.getUser())
                    .scheduledAt(scheduledAt)
                    .status(DoseStatus.missed)
                    .build());

            var recentMisses = recordRepository
                    .countByMedicationIdAndStatusAndScheduledAtAfter(
                            medication.getId(),
                            DoseStatus.missed,
                            LocalDateTime.of(today.minusDays(1), LocalTime.MIN)
                    );

            if (recentMisses >= 3) {
                System.out.println("ALERT: " + recentMisses + " misses for " + medication.getName() + " — sending email");
                alertService.notifyEmergencyContacts(
                        medication.getUser(),
                        medication.getName(),
                        recentMisses.intValue()
                );
            }
        }
    }


}

