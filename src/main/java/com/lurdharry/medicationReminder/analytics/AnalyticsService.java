package com.lurdharry.medicationReminder.analytics;

import com.lurdharry.medicationReminder.analytics.dto.AdherenceResponse;
import com.lurdharry.medicationReminder.analytics.dto.MedicationAdherence;
import com.lurdharry.medicationReminder.doserecord.DoseRecordRepository;
import com.lurdharry.medicationReminder.medication.MedicationRepository;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final DoseRecordRepository doseRecordRepository;
    private final MedicationRepository medicationRepository;

    public AdherenceResponse getAdherence(User user, int days) {
        var since = LocalDateTime.now().minusDays(days);

        var overall = getOverallCounts(user.getId(), since);
        var perMedication = getPerMedicationCounts(user.getId(), since);


        return AdherenceResponse.builder()
                .totalDoses(overall.get("total"))
                .taken(overall.get("taken"))
                .skipped(overall.get("skipped"))
                .missed(overall.get("missed"))
                .adherenceRate(overall.get("total") > 0 ? (overall.get("taken") * 100.0 / overall.get("total")) : 0)
                .currentStreak(calculateStreak(user))
                .perMedication(perMedication)
                .build();
    }

    private Map<String, Integer> getOverallCounts(UUID userId, LocalDateTime since) {
        var statusCounts = doseRecordRepository.countByStatus(userId, since);
        int taken = 0, skipped = 0, missed = 0;
        for (var row : statusCounts) {
            switch (row.getStatus()) {
                case "taken" -> taken = row.getCount().intValue();
                case "skipped" -> skipped = row.getCount().intValue();
                case "missed" -> missed = row.getCount().intValue();
            }
        }
        return Map.of("taken", taken, "skipped", skipped, "missed", missed, "total", taken + skipped + missed);
    }

    private List<MedicationAdherence> getPerMedicationCounts(UUID userId, LocalDateTime since) {
        var medStatusCounts = doseRecordRepository.countByMedicationAndStatus(userId, since);
        Map<UUID, Map<String, Integer>> medMap = new HashMap<>();
        for (var row : medStatusCounts) {
            medMap.computeIfAbsent(row.getMedicationId(), k -> new HashMap<>())
                    .put(row.getStatus(), row.getCount().intValue());
        }

        return medicationRepository.findByUserId(userId).stream()
                .map(med -> {
                    var counts = medMap.getOrDefault(med.getId(), Map.of());
                    int medTaken = counts.getOrDefault("taken", 0);
                    int medSkipped = counts.getOrDefault("skipped", 0);
                    int medMissed = counts.getOrDefault("missed", 0);
                    int medTotal = medTaken + medSkipped + medMissed;
                    double medRate = medTotal > 0 ? (medTaken * 100.0 / medTotal) : 0;

                    return MedicationAdherence.builder()
                            .medicationId(med.getId())
                            .medicationName(med.getName())
                            .totalDoses(medTotal)
                            .taken(medTaken)
                            .skipped(medSkipped)
                            .missed(medMissed)
                            .adherenceRate(medRate)
                            .build();
                }).toList();
    }

    private int calculateStreak(User user) {
        var completedDates = doseRecordRepository.findCompletedDates(user.getId());

        if (completedDates.isEmpty()) return 0;

        int streak = 0;
        var date = LocalDate.now();

        for (LocalDate completed : completedDates) {
            if (completed.equals(date)) {
                streak++;
                date = date.minusDays(1);
            } else {
                break;
            }
        }
        return streak;
    }
}
