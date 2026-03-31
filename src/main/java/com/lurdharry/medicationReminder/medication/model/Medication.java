package com.lurdharry.medicationReminder.medication.model;

import com.lurdharry.medicationReminder.user.model.User;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String dosage;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MedicationUnit unit;

    @Column(nullable = false)
    private String purpose;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    
    private String instruction;

    private String pharmacyInfo;

    private String imageUrl;

    private String color;

    private String shape;

    private Boolean active;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalDate refillDate;

    private String prescribedBy;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(insertable = false, nullable = false)
    private LocalDateTime updatedAt;
}
