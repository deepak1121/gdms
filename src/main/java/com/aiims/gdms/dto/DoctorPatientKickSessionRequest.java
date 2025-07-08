package com.aiims.gdms.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public class DoctorPatientKickSessionRequest {

    private Long patientId;
    
    @NotNull(message = "fromDate is required")
    private LocalDate fromDate;
    private LocalDate toDate;

    // Getters and Setters

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
