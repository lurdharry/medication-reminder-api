package com.lurdharry.medicationReminder.medication;


import com.lurdharry.medicationReminder.exception.dto.ResponseDTO;
import com.lurdharry.medicationReminder.medication.dto.MedicationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/medication")
public class MedicationController {
    private final MedicationService service;

    @PostMapping("/add")
    public ResponseEntity<ResponseDTO> addMedication (MedicationRequest request){



    }


}
