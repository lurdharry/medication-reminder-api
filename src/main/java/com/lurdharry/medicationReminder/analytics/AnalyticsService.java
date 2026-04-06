package com.lurdharry.medicationReminder.analytics;

import com.lurdharry.medicationReminder.analytics.dto.*;
import com.lurdharry.medicationReminder.doserecord.DoseRecordRepository;
import com.lurdharry.medicationReminder.doserecord.dto.StatusCount;
import com.lurdharry.medicationReminder.medication.MedicationRepository;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

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

    public PatternResponse getPatterns(User user, int days) {
        var since = LocalDateTime.now().minusDays(days);
        var userId = user.getId();

        // Time slot analysis
        var timeSlotData = doseRecordRepository.countByTimeSlotAndStatus(userId, since);
        String bestTime = findBestSlot(timeSlotData);
        String worstTime = findWorstSlot(timeSlotData);

        // Day of week analysis
        var dayData = doseRecordRepository.countByDayAndStatus(userId, since);
        String bestDay = findBestDay(dayData);

        // Trend
        var midpoint = LocalDateTime.now().minusDays(days / 2);
        var firstHalf = doseRecordRepository.countByStatus(userId, since);
        var secondHalf = doseRecordRepository.countByStatus(userId, midpoint);
        String trend = calculateTrend(firstHalf, secondHalf);

        // Suggestions
        List<String> suggestions = generateSuggestions(timeSlotData, dayData, trend);

        return PatternResponse.builder()
                .bestTimeOfDay(bestTime)
                .worstTimeOfDay(worstTime)
                .bestDayOfWeek(bestDay)
                .adherenceTrend(trend)
                .suggestions(suggestions)
                .build();
    }

    private String calculateTrend(List<StatusCount> firstHalf, List<StatusCount> secondHalf) {
        int firstTaken = 0, firstTotal = 0, secondTaken = 0, secondTotal = 0;

        for (var row : firstHalf) {
            int count = row.getCount().intValue();
            firstTotal += count;
            if ("taken".equals(row.getStatus())) firstTaken = count;
        }
        for (var row : secondHalf) {
            int count = row.getCount().intValue();
            secondTotal += count;
            if ("taken".equals(row.getStatus())) secondTaken = count;
        }

        if (firstTotal == 0 || secondTotal == 0) return "stable";

        double firstRate = (double) firstTaken / firstTotal;
        double secondRate = (double) secondTaken / secondTotal;
        double diff = secondRate - firstRate;

        if (diff > 0.1) return "improving";
        if (diff < -0.1) return "declining";
        return "stable";
    }

    private String findBestSlot(List<TimeSlotCount> data) {
        Map<String, int[]> slots = new HashMap<>();
        for (var row : data) {
            slots.computeIfAbsent(row.getTimeSlot(), k -> new int[2]);
            if ("taken".equals(row.getStatus())) slots.get(row.getTimeSlot())[0] = row.getCount().intValue();
            slots.get(row.getTimeSlot())[1] += row.getCount().intValue();
        }

        return slots.entrySet().stream()
                .filter(e -> e.getValue()[1] > 0)
                .max(Comparator.comparingDouble(e -> (double) e.getValue()[0] / e.getValue()[1]))
                .map(Map.Entry::getKey)
                .orElse("morning");
    }

    private String findWorstSlot(List<TimeSlotCount> data) {
        Map<String, int[]> slots = new HashMap<>();
        for (var row : data) {
            slots.computeIfAbsent(row.getTimeSlot(), k -> new int[2]);
            if ("missed".equals(row.getStatus())) slots.get(row.getTimeSlot())[0] = row.getCount().intValue();
            slots.get(row.getTimeSlot())[1] += row.getCount().intValue();
        }

        return slots.entrySet().stream()
                .filter(e -> e.getValue()[1] > 0)
                .max(Comparator.comparingDouble(e -> (double) e.getValue()[0] / e.getValue()[1]))
                .map(Map.Entry::getKey)
                .orElse("evening");
    }

    private String findBestDay(List<DayCount> data) {
        Map<String, int[]> days = new HashMap<>();
        for (var row : data) {
            days.computeIfAbsent(row.getDayName(), k -> new int[2]);
            if ("taken".equals(row.getStatus())) days.get(row.getDayName())[0] = row.getCount().intValue();
            days.get(row.getDayName())[1] += row.getCount().intValue();
        }

        return days.entrySet().stream()
                .filter(e -> e.getValue()[1] > 0)
                .max(Comparator.comparingDouble(e -> (double) e.getValue()[0] / e.getValue()[1]))
                .map(Map.Entry::getKey)
                .orElse("Monday");
    }

    private List<String> generateSuggestions(List<TimeSlotCount> timeData, List<DayCount> dayData, String trend) {
        List<String> suggestions = new ArrayList<>();

        for (var row : timeData) {
            if ("morning".equals(row.getTimeSlot()) && "missed".equals(row.getStatus()) && row.getCount() > 5) {
                suggestions.add("Try placing your morning medications next to your breakfast items");
                break;
            }
        }

        for (var row : timeData) {
            if ("evening".equals(row.getTimeSlot()) && "missed".equals(row.getStatus()) && row.getCount() > 5) {
                suggestions.add("Consider setting an alarm for evening medications during dinner");
                break;
            }
        }

        if ("improving".equals(trend)) {
            suggestions.add("Great progress! Your adherence is improving");
        } else if ("declining".equals(trend)) {
            suggestions.add("Your adherence has been dropping. Try setting consistent reminders");
        }

        return suggestions.isEmpty() ? List.of("Continue with your current routine!") : suggestions;
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
