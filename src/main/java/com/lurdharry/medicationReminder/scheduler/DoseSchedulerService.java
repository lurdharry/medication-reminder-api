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
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoseSchedulerService {

    private final DoseScheduleRepository scheduleRepository;
    private final DoseRecordRepository recordRepository;
    private final MedicationRepository medicationRepository;
    private final AlertService alertService;

    @Scheduled(cron = "0 0 10,16,23 * * *")
    public void checkMissedDoses() {
        System.out.println("Scheduler running at: " + LocalTime.now());
        var now = LocalTime.now();
        var today = LocalDate.now();

        var overdueSchedules = scheduleRepository.findOverdueSchedules(now);

        if (overdueSchedules.isEmpty()) return;

        var scheduleIds = overdueSchedules.stream()
                .map(DoseSchedule::getId)
                .toList();

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

            // Check consecutive misses
            var recentRecords = recordRepository
                    .findTop5ByMedicationIdOrderByScheduledAtDesc(medication.getId());

            int consecutiveMisses = 0;
            for (var record : recentRecords) {
                if (record.getStatus() == DoseStatus.missed) {
                    consecutiveMisses++;
                } else {
                    break;
                }
            }

            if (consecutiveMisses >= 2) {
                List<LocalTime> missedTimes = recentRecords.stream()
                        .limit(consecutiveMisses)
                        .map(r -> r.getScheduledAt().toLocalTime())
                        .toList();

                System.out.println("ALERT: " + consecutiveMisses + " consecutive misses for " + medication.getName());
                alertService.notifyEmergencyContacts(
                        medication.getUser(),
                        medication.getName(),
                        consecutiveMisses,
                        missedTimes
                );
            }
        }
    }
}


