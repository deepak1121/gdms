package com.aiims.gdms.entity;

import jakarta.persistence.*;


import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "symptom_logs")
public class SymptomLog {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @JsonIgnore
    private User patient;
    
    @ElementCollection
    @CollectionTable(name = "symptom_log_symptoms", joinColumns = @JoinColumn(name = "symptom_log_id"))
    @Column(name = "symptom")
    private List<String> symptoms;
    
    @Column(nullable = false)
    private LocalDateTime timestamp;

    private boolean noSymptoms;
    
    @Column
    private String severity;
    
    @Column
    private String notes;

    
    // Constructors
    public SymptomLog() {}
    
    public SymptomLog(User patient, List<String> symptoms) {
        this.patient = patient;
        this.symptoms = symptoms;
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
    
    public List<String> getSymptoms() {
        return symptoms;
    }
    
    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public boolean isNoSymptoms() {
        return noSymptoms;
    }

    public void setNoSymptoms(boolean noSymptoms) {
        this.noSymptoms = noSymptoms;
    }

} 