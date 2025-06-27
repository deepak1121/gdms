package com.aiims.gdms.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * DTO for kick count log requests from patients
 */
public class KickCountLogRequest {
    
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    
    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
    
    @NotNull(message = "Kick count is required")
    @Positive(message = "Kick count must be positive")
    private Integer kickCount;
    
    @Size(max = 500, message = "Notes must not exceed 500 characters")
    private String notes;
    
    // Constructors
    public KickCountLogRequest() {}
    
    public KickCountLogRequest(LocalDateTime startTime, LocalDateTime endTime, Integer kickCount, String notes) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.kickCount = kickCount;
        this.notes = notes;
    }
    
    // Getters and Setters
    public LocalDateTime getStartTime() {
        return startTime;
    }
    
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }
    
    public LocalDateTime getEndTime() {
        return endTime;
    }
    
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
    
    public Integer getKickCount() {
        return kickCount;
    }
    
    public void setKickCount(Integer kickCount) {
        this.kickCount = kickCount;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
} 