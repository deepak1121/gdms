package com.aiims.gdms.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "clinical_notes")
public class ClinicalNotes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;


    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    
    public ClinicalNotes() {
    }

    public ClinicalNotes(User doctor, User patient, LocalDateTime createdAt, String notes) {
        this.doctor = doctor;
        this.patient = patient;
        this.createdAt = createdAt;
        this.notes = notes;
    }

    
    public Long getId() {
        return id;
    }

    public User getDoctor() {
        return doctor;
    }

    public void setDoctor(User doctor) {
        this.doctor = doctor;
    }

    public User getPatient() {
        return patient;
    }

    public void setPatient(User patient) {
        this.patient = patient;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
