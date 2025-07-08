package com.aiims.gdms.dto;

import java.util.List;

import com.aiims.gdms.entity.SymptomLog;

public class AllDoctorPatientLogsResponse {
	
	
	 private List<GlucoseLogResponse> glucoseLogs;
	    private List<MealLogDto> mealLogs;
	    private List<SymptomLog> symptomLogs;
	    private List<DoctorPatientKickSessionDto> kickSessions;

	    public List<GlucoseLogResponse> getGlucoseLogs() {
	        return glucoseLogs;
	    }

	    public void setGlucoseLogs(List<GlucoseLogResponse> glucoseLogs) {
	        this.glucoseLogs = glucoseLogs;
	    }

	    public List<MealLogDto> getMealLogs() {
	        return mealLogs;
	    }

	    public void setMealLogs(List<MealLogDto> mealLogs) {
	        this.mealLogs = mealLogs;
	    }

	    public List<SymptomLog> getSymptomLogs() {
	        return symptomLogs;
	    }

	    public void setSymptomLogs(List<SymptomLog> symptomLogs) {
	        this.symptomLogs = symptomLogs;
	    }

	    public List<DoctorPatientKickSessionDto> getKickSessions() {
	        return kickSessions;
	    }

	    public void setKickSessions(List<DoctorPatientKickSessionDto> kickSessions) {
	        this.kickSessions = kickSessions;
	    }
	

}
