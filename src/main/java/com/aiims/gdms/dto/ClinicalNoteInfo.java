package com.aiims.gdms.dto;

import java.time.LocalDateTime;

public class ClinicalNoteInfo {
	
	private String notes;
    private LocalDateTime createdAt;

    public ClinicalNoteInfo(String notes, LocalDateTime createdAt) {
        this.notes = notes;
        this.createdAt = createdAt;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

}
