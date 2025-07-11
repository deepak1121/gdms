package com.aiims.gdms.dto;

import java.time.LocalDateTime;

public class ClinicalNoteInfo {
	
	private String notes;
    private LocalDateTime createdAt;
    private String doctorUsername;

    public ClinicalNoteInfo(String notes, LocalDateTime createdAt, String doctorUsername) {
        this.notes = notes;
        this.createdAt = createdAt;
        this.setDoctorUsername(doctorUsername);
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

	public String getDoctorUsername() {
		return doctorUsername;
	}

	public void setDoctorUsername(String doctorUsername) {
		this.doctorUsername = doctorUsername;
	}

}
