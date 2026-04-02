package com.lurdharry.medicationReminder.emergencycontact;

import com.lurdharry.medicationReminder.emergencycontact.model.EmergencyContact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, UUID> {
    List<EmergencyContact> findByUserId(UUID userId);
    List<EmergencyContact> findByUserIdAndNotifyOnMissedDoseTrue(UUID userId);
}

