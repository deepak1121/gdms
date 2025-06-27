package com.aiims.gdms.dto;

import com.aiims.gdms.entity.GlucoseLog;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO for glucose log requests from patients
 */
public class GlucoseLogRequest {
    
    @NotNull(message = "Glucose level is required")
    @Positive(message = "Glucose level must be positive")
    private Double glucoseLevel;
    
    @NotNull(message = "Reading type is required")
    private GlucoseLog.ReadingType readingType;
    
    private String notes;
    
    // Constructors
    public GlucoseLogRequest() {}
    
    public GlucoseLogRequest(Double glucoseLevel, GlucoseLog.ReadingType readingType, String notes) {
        this.glucoseLevel = glucoseLevel;
        this.readingType = readingType;
        this.notes = notes;
    }
    
    // Getters and Setters
    public Double getGlucoseLevel() {
        return glucoseLevel;
    }
    
    public void setGlucoseLevel(Double glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }
    
    public GlucoseLog.ReadingType getReadingType() {
        return readingType;
    }
    
    public void setReadingType(GlucoseLog.ReadingType readingType) {
        this.readingType = readingType;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
} 