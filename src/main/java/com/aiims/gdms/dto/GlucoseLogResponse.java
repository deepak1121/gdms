package com.aiims.gdms.dto;

import com.aiims.gdms.entity.GlucoseLog;

import java.time.LocalDateTime;

/**
 * DTO for glucose log responses
 */
public class GlucoseLogResponse {
    
    private Long id;
    private Double glucoseLevel;
    private GlucoseLog.ReadingType readingType;
    private LocalDateTime timestamp;
    private String notes;
    
    // Constructors
    public GlucoseLogResponse() {}
    
    public GlucoseLogResponse(GlucoseLog glucoseLog) {
        this.id = glucoseLog.getId();
        this.glucoseLevel = glucoseLog.getGlucoseLevel();
        this.readingType = glucoseLog.getReadingType();
        this.timestamp = glucoseLog.getTimestamp();
        this.notes = glucoseLog.getNotes();
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
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
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
} 