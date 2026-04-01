package com.lurdharry.medicationReminder.doseschedule;

import com.lurdharry.medicationReminder.doseschedule.dto.DoseScheduleRequest;
import com.lurdharry.medicationReminder.doseschedule.dto.DoseScheduleResponse;
import com.lurdharry.medicationReminder.doseschedule.model.DoseSchedule;
import com.lurdharry.medicationReminder.exception.ResponseException;
import com.lurdharry.medicationReminder.medication.MedicationService;
import com.lurdharry.medicationReminder.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoseScheduleService {

    private final DoseScheduleRepository repository;
    private final DoseScheduleMapper mapper;
    private final MedicationService medicationService;

    public DoseScheduleResponse addSchedule(UUID medicationId, DoseScheduleRequest request, User user) {
        var medication = medicationService.findMedicationByIdAndUser(medicationId, user);
        var schedule = DoseSchedule.builder()
                .medication(medication)
                .time(request.time())
                .build();
        repository.save(schedule);
        return mapper.toResponse(schedule);
    }

    public List<DoseScheduleResponse> getSchedules(UUID medicationId, User user) {
        medicationService.findMedicationByIdAndUser(medicationId, user);
        return repository.findByMedicationId(medicationId)
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    public void deleteSchedule(UUID medicationId, UUID id, User user) {
        medicationService.findMedicationByIdAndUser(medicationId, user);
        var schedule = repository.findById(id)
                .orElseThrow(() -> new ResponseException("Schedule not found", HttpStatus.NOT_FOUND));
        repository.delete(schedule);
    }

    public DoseScheduleResponse updateSchedule(UUID medicationId, UUID id, DoseScheduleRequest request, User user) {
        medicationService.findMedicationByIdAndUser(medicationId, user);
        
        var schedule = repository.findById(id)
                .orElseThrow(() -> new ResponseException("Schedule not found", HttpStatus.NOT_FOUND));
        schedule.setTime(request.time());
        repository.save(schedule);

        return mapper.toResponse(schedule);
    }
}
