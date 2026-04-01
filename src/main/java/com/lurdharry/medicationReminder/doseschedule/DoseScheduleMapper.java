package com.lurdharry.medicationReminder.doseschedule;

import com.lurdharry.medicationReminder.doseschedule.dto.DoseScheduleResponse;
import com.lurdharry.medicationReminder.doseschedule.model.DoseSchedule;
import org.springframework.stereotype.Component;

@Component
public class DoseScheduleMapper {

    public DoseScheduleResponse toResponse(DoseSchedule schedule) {
        return DoseScheduleResponse.builder()
                .id(schedule.getId())
                .medicationId(schedule.getMedication().getId())
                .time(schedule.getTime())
                .createdAt(schedule.getCreatedAt())
                .build();
    }
}
