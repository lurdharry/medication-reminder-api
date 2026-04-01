package com.lurdharry.medicationReminder.doseschedule;


import com.lurdharry.medicationReminder.doseschedule.dto.DoseScheduleRequest;
import com.lurdharry.medicationReminder.doseschedule.model.DoseSchedule;
import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import com.lurdharry.medicationReminder.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/medication/{medicationId}/schedules")
public class DoseScheduleController {
    private final DoseScheduleService service;

    @PostMapping
    public ResponseEntity<ResponseDTO> addSchedule(
            @PathVariable UUID medicationId,
            @RequestBody @Valid DoseScheduleRequest request,
            @AuthenticationPrincipal User user
    ) {
        var res = service.addSchedule(medicationId, request, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Schedule added", res);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<ResponseDTO> getSchedules(
            @PathVariable UUID medicationId,
            @AuthenticationPrincipal User user
    ) {
        var res = service.getSchedules(medicationId, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Schedules retrieved", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateSchedule(
            @PathVariable UUID medicationId,
            @PathVariable UUID id,
            @RequestBody @Valid DoseScheduleRequest request,
            @AuthenticationPrincipal User user
    ) {
        var res = service.updateSchedule(medicationId, id, request, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Schedule updated", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteSchedule(
            @PathVariable UUID medicationId,
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        service.deleteSchedule(medicationId, id, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Schedule deleted", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
