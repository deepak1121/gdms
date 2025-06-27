package com.aiims.gdms.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDateTime;

@Entity
@Table(name = "glucose_logs")
public class GlucoseLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;
    
    @NotNull
    @Positive
    @Column(nullable = false)
    private Double glucoseLevel;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReadingType readingType;
    
    @Column
    private String notes;
    
    public enum ReadingType {
        FASTING, POST_BREAKFAST, POST_LUNCH, POST_DINNER, BEDTIME
    }
    
    // Constructors
    public GlucoseLog() {}
    
    public GlucoseLog(User patient, Double glucoseLevel, ReadingType readingType) {
        this.patient = patient;
        this.glucoseLevel = glucoseLevel;
        this.readingType = readingType;
        this.timestamp = LocalDateTime.now();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getPatient() {
        return patient;
    }
    
    public void setPatient(User patient) {
        this.patient = patient;
    }
    
    public Double getGlucoseLevel() {
        return glucoseLevel;
    }
    
    public void setGlucoseLevel(Double glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public ReadingType getReadingType() {
        return readingType;
    }
    
    public void setReadingType(ReadingType readingType) {
        this.readingType = readingType;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
} 