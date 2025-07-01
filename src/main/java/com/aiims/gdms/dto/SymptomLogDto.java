package com.aiims.gdms.dto;

import java.util.List;

public class SymptomLogDto {

    private List<String> symptoms;
    private String severity;
    private boolean noSymptoms;
    private String notes;

    public SymptomLogDto() {
    }

    public SymptomLogDto(List<String> symptoms, String severity, boolean noSymptoms, String notes) {
        this.symptoms = symptoms;
        this.severity = severity;
        this.noSymptoms = noSymptoms;
        this.notes = notes;
    }

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

    public boolean isNoSymptoms() {
        return noSymptoms;
    }

    public void setNoSymptoms(boolean noSymptoms) {
        this.noSymptoms = noSymptoms;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
