package com.lurdharry.medicationReminder.medication;


import com.lurdharry.medicationReminder.medication.dto.MedicationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository repository;
    private final MedicationMapper mapper;

    public MedicationResponse addMedication(
            MedicationRequest request
    ){
        return mapper.toMedicationResponse(request)
    }

}
