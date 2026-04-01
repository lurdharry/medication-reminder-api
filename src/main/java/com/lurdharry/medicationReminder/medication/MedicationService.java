package com.lurdharry.medicationReminder.medication;


import com.lurdharry.medicationReminder.exception.ResponseException;
import com.lurdharry.medicationReminder.medication.dto.MedicationRequest;
import com.lurdharry.medicationReminder.medication.dto.MedicationResponse;
import com.lurdharry.medicationReminder.medication.dto.MedicationUpdateRequest;
import com.lurdharry.medicationReminder.medication.model.Medication;
import com.lurdharry.medicationReminder.user.model.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MedicationService {

    private final MedicationRepository repository;
    private final MedicationMapper mapper;

    public MedicationResponse addMedication(
            MedicationRequest request, User user
            ){

        Medication medication = mapper.toMedication(request);
        medication.setActive(true);
        medication.setUser(user);
        var res = repository.save(medication);

        return mapper.toMedicationResponse(res);
    }

    public List<MedicationResponse> getAllMedications(User user) {
        return repository.findByUserId(user.getId())
                .stream()
                .map(mapper::toMedicationResponse)
                .toList();
    }

    public MedicationResponse getMedicationById(@Valid UUID id, User user) {
        var medication = findMedicationByIdAndUser(id, user);
        return mapper.toMedicationResponse(medication);
    }

    public MedicationResponse updateMedication(UUID id, @Valid MedicationUpdateRequest request, User user) {
        var medication = findMedicationByIdAndUser(id, user);
        mapper.updateMedicationFromRequest(request, medication);
        repository.save(medication);
        return mapper.toMedicationResponse(medication);
    }

    public void deleteMedication(UUID id, User user) {
        var medication = findMedicationByIdAndUser(id, user);
        repository.delete(medication);
    }

    public Medication findMedicationByIdAndUser(UUID id, User user) {
        var medication = repository.findById(id).orElseThrow(
                ()-> new ResponseException("Unable to find medication with "+ id, HttpStatus.NOT_FOUND)
        );
        if (!medication.getUser().getId().equals(user.getId())) {
            throw new ResponseException("Not authorized", HttpStatus.FORBIDDEN);
        }
        return medication;
    }
}
