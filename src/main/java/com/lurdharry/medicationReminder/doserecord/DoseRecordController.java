package com.lurdharry.medicationReminder.doserecord;

import com.lurdharry.medicationReminder.doserecord.dto.DoseRecordRequest;
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
@RequestMapping("/api/doses")
public class DoseRecordController {

    private final DoseRecordService service;

    @PostMapping("/{scheduleId}/record")
    public ResponseEntity<ResponseDTO> recordDose(
            @PathVariable UUID scheduleId,
            @RequestBody @Valid DoseRecordRequest request,
            @AuthenticationPrincipal User user
    ) {
        var res = service.recordDose(scheduleId, request, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Dose recorded", res);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseDTO> getHistory(@AuthenticationPrincipal User user) {
        var res = service.getHistory(user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "History retrieved", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/history/{medicationId}")
    public ResponseEntity<ResponseDTO> getHistoryByMedication(
            @PathVariable UUID medicationId,
            @AuthenticationPrincipal User user
    ) {
        var res = service.getHistoryByMedication(medicationId, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "History retrieved", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
