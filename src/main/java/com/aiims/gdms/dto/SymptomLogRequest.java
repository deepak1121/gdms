package com.aiims.gdms.dto;

import jakarta.validation.constraints.Size;

import java.util.List;


public class SymptomLogRequest {
    
    private List<String> symptoms;
    
    @Size(max = 50, message = "Severity description must not exceed 50 characters")
    private String severity;

    private boolean noSymptoms;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    
    // Constructors
    public SymptomLogRequest() {}
    
    public SymptomLogRequest(List<String> symptoms, String severity, String notes) {
        this.symptoms = symptoms;
        this.severity = severity;
        this.notes = notes;
    }
    
    // Getters and Setters
    public List<String> getSymptoms() {
        return symptoms;
    }
    
    public void setSymptoms(List<String> symptoms) {
        this.symptoms = symptoms;
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