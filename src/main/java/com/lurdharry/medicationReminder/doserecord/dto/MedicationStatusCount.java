package com.lurdharry.medicationReminder.doserecord.dto;

import java.util.UUID;

public interface MedicationStatusCount {
    UUID getMedicationId();
    String getStatus();
    Integer getCount();
}
