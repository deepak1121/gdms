package com.aiims.gdms.dto;

import com.aiims.gdms.entity.KickSession;

import java.time.LocalDateTime;

public class DoctorPatientKickSessionDto {

    private Long sessionId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private boolean completed;
    private int kickCount;

    public DoctorPatientKickSessionDto(KickSession session) {
        this.sessionId = session.getId();
        this.startTime = session.getStartTime();
        this.endTime = session.getEndTime();
        this.completed = session.isCompleted();
        this.kickCount = session.getKicks() != null ? session.getKicks().size() : 0;
    }

    // Getters and Setters

    public Long getSessionId() {
        return sessionId;
    }

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

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

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public int getKickCount() {
        return kickCount;
    }

    public void setKickCount(int kickCount) {
        this.kickCount = kickCount;
    }
}
