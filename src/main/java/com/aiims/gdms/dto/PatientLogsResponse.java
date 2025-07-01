package com.aiims.gdms.dto;

import java.util.List;

public class PatientLogsResponse {

    private List<KickCountLogDto> kickCounts;
    private List<MealLogDto> meals;
    private List<GlucoseLogDto> glucoseLogs;
    private List<SymptomLogDto> symptomLogs;
	public List<KickCountLogDto> getKickCounts() {
		return kickCounts;
	}
	public void setKickCounts(List<KickCountLogDto> kickCounts) {
		this.kickCounts = kickCounts;
	}
	public List<MealLogDto> getMeals() {
		return meals;
	}
	public void setMeals(List<MealLogDto> meals) {
		this.meals = meals;
	}
	public List<GlucoseLogDto> getGlucoseLogs() {
		return glucoseLogs;
	}
	public void setGlucoseLogs(List<GlucoseLogDto> glucoseLogs) {
		this.glucoseLogs = glucoseLogs;
	}
	public List<SymptomLogDto> getSymptomLogs() {
		return symptomLogs;
	}
	public void setSymptomLogs(List<SymptomLogDto> symptomLogs) {
		this.symptomLogs = symptomLogs;
	}

    // Getters and Setters
    
    
    
}
