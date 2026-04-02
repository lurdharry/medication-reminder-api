package com.lurdharry.medicationReminder.emergencycontact;


import com.lurdharry.medicationReminder.emergencycontact.dto.EmergencyContactRequest;
import com.lurdharry.medicationReminder.emergencycontact.dto.EmergencyContactResponse;
import com.lurdharry.medicationReminder.emergencycontact.model.EmergencyContact;
import org.springframework.stereotype.Component;

@Component
public class EmergencyContactMapper {

    public EmergencyContact toEntity(EmergencyContactRequest request) {
        return EmergencyContact.builder()
                .name(request.name())
                .relationship(request.relationship())
                .phone(request.phone())
                .email(request.email())
                .isPrimary(request.isPrimary() != null ? request.isPrimary() : false)
                .notifyOnMissedDose(request.notifyOnMissedDose() != null ? request.notifyOnMissedDose() : false)
                .build();
    }

    public EmergencyContactResponse toResponse(EmergencyContact contact) {
        return EmergencyContactResponse.builder()
                .id(contact.getId())
                .name(contact.getName())
                .relationship(contact.getRelationship())
                .phone(contact.getPhone())
                .email(contact.getEmail())
                .isPrimary(contact.getIsPrimary())
                .notifyOnMissedDose(contact.getNotifyOnMissedDose())
                .createdAt(contact.getCreatedAt())
                .build();
    }
}

