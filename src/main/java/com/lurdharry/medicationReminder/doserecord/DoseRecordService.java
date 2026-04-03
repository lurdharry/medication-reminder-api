package com.lurdharry.medicationReminder.doserecord;


import com.lurdharry.medicationReminder.doserecord.dto.DoseRecordRequest;
import com.lurdharry.medicationReminder.doserecord.dto.DoseRecordResponse;
import com.lurdharry.medicationReminder.doserecord.model.DoseRecord;
import com.lurdharry.medicationReminder.doseschedule.DoseScheduleRepository;
import com.lurdharry.medicationReminder.exception.ResponseException;
import com.lurdharry.medicationReminder.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoseRecordService {

    private final DoseRecordRepository repository;
    private final DoseRecordMapper mapper;
    private final DoseScheduleRepository scheduleRepository;

    public DoseRecordResponse recordDose(UUID scheduleId, DoseRecordRequest request, User user) {
        var schedule = scheduleRepository.findById(scheduleId)
                .orElseThrow(() -> new ResponseException("Schedule not found", HttpStatus.NOT_FOUND));

        var scheduledAt = LocalDateTime.of(LocalDate.now(), schedule.getTime());

        // Check if record already exists for today
        var existing = repository.findByDoseScheduleIdAndScheduledAt(scheduleId, scheduledAt);

        if (existing.isPresent()) {
            // Update existing record
            var record = existing.get();
            record.setStatus(request.status());
            record.setRecordedAt(LocalDateTime.now());
            repository.save(record);
            return mapper.toResponse(record);
        }

        // Create new record
        var record = DoseRecord.builder()
                .doseSchedule(schedule)
                .medication(schedule.getMedication())
                .user(user)
                .scheduledAt(LocalDateTime.of(LocalDate.now(), schedule.getTime()))
                .status(request.status())
                .recordedAt(LocalDateTime.now())
                .build();

        repository.save(record);
        return mapper.toResponse(record);
    }

    public List<DoseRecordResponse> getHistory(User user) {
        return repository.findByUserId(user.getId())
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public List<DoseRecordResponse> getHistoryByMedication(UUID medicationId, User user) {
        return repository.findByMedicationId(medicationId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }
}
