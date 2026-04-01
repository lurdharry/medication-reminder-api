package com.lurdharry.medicationReminder.doserecord.model;

import com.lurdharry.medicationReminder.doseschedule.model.DoseSchedule;
import com.lurdharry.medicationReminder.medication.model.Medication;
import com.lurdharry.medicationReminder.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "dose_records")
public class DoseRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dose_schedule_id", nullable = false)
    private DoseSchedule doseSchedule;

    @ManyToOne
    @JoinColumn(name = "medication_id", nullable = false)
    private Medication medication;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DoseStatus status;

    private LocalDateTime recordedAt;

    @CreatedDate
    private LocalDateTime createdAt;
}