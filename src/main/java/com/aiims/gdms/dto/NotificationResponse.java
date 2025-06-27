package com.aiims.gdms.dto;

import java.util.List;

public class NotificationResponse {
    
    private List<String> missingLogs;
    private List<String> reminders;
    private boolean hasNotifications;
    
    // Constructors
    public NotificationResponse() {}
    
    public NotificationResponse(List<String> missingLogs, List<String> reminders) {
        this.missingLogs = missingLogs;
        this.reminders = reminders;
        this.hasNotifications = !missingLogs.isEmpty() || !reminders.isEmpty();
    }
    
    // Getters and Setters
    public List<String> getMissingLogs() {
        return missingLogs;
    }
    
    public void setMissingLogs(List<String> missingLogs) {
        this.missingLogs = missingLogs;
    }
    
    public List<String> getReminders() {
        return reminders;
    }
    
    public void setReminders(List<String> reminders) {
        this.reminders = reminders;
    }
    
    public boolean isHasNotifications() {
        return hasNotifications;
    }
    
    public void setHasNotifications(boolean hasNotifications) {
        this.hasNotifications = hasNotifications;
    }
} 