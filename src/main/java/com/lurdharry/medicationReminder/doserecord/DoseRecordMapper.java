package com.lurdharry.medicationReminder.doserecord;

import com.lurdharry.medicationReminder.doserecord.dto.DoseRecordResponse;
import com.lurdharry.medicationReminder.doserecord.model.DoseRecord;
import org.springframework.stereotype.Component;

@Component
public class DoseRecordMapper {

    public DoseRecordResponse toResponse(DoseRecord record) {
        return DoseRecordResponse.builder()
                .id(record.getId())
                .doseScheduleId(record.getDoseSchedule().getId())
                .medicationId(record.getMedication().getId())
                .userId(record.getUser().getId())
                .scheduledAt(record.getScheduledAt())
                .status(record.getStatus())
                .recordedAt(record.getRecordedAt())
                .createdAt(record.getCreatedAt())
                .build();
    }
}
