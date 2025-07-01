package com.aiims.gdms.dto;

import java.time.LocalDateTime;

public class KickCountLogDto {

    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int kickCount;

    public KickCountLogDto() {
    }

    public KickCountLogDto(LocalDateTime startTime, LocalDateTime endTime, int kickCount) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.kickCount = kickCount;
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

    public int getKickCount() {
        return kickCount;
    }

    public void setKickCount(int kickCount) {
        this.kickCount = kickCount;
    }
}
