package com.lurdharry.medicationReminder.medication;


import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import com.lurdharry.medicationReminder.medication.dto.MedicationRequest;
import com.lurdharry.medicationReminder.medication.dto.MedicationUpdateRequest;
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
@RequestMapping("api/medication")
public class MedicationController {

    private final MedicationService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addMedication (
            @RequestBody @Valid MedicationRequest request,
            @AuthenticationPrincipal User user
    ){

        var res = service.addMedication(request,user);
        ResponseDTO response = new ResponseDTO(HttpStatus.CREATED.value(), "Medication added", res);

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDTO> getAllMedications(@AuthenticationPrincipal User user){
        var res = service.getAllMedications(user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "All Medications retrieved", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO> getAllMedicationById(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ){
        var res = service.getMedicationById(id,user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Medications retrieved", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateMedication(
            @PathVariable UUID id,
            @RequestBody @Valid MedicationUpdateRequest request,
            @AuthenticationPrincipal User user
    ){
        var res = service.updateMedication(id, request, user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Medication updated", res);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> removeMedication(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ){
        service.deleteMedication(id,user);
        ResponseDTO response = new ResponseDTO(HttpStatus.OK.value(), "Medication deleted",null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
