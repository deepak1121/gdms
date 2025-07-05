package com.aiims.gdms.dto;

import java.time.LocalDate;

public class DoctorPatientSymptomRequest {
    private Long patientId;
    private LocalDate date; // Optional

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
