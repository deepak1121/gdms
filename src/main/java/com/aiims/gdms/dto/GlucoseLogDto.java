package com.aiims.gdms.dto;

public class GlucoseLogDto {

    private Double glucoseLevel;
    private String readingType;

    public GlucoseLogDto() {
    }

    public GlucoseLogDto(Double glucoseLevel, String readingType) {
        this.glucoseLevel = glucoseLevel;
        this.readingType = readingType;
    }

    public Double getGlucoseLevel() {
        return glucoseLevel;
    }

    public void setGlucoseLevel(Double glucoseLevel) {
        this.glucoseLevel = glucoseLevel;
    }

    public String getReadingType() {
        return readingType;
    }

    public void setReadingType(String readingType) {
        this.readingType = readingType;
    }
}
