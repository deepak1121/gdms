package com.aiims.gdms.dto;

public class AuthResponse {
    
    private String token;
    private String username;
    private String role;
    private String message;
    
    // Constructors
    public AuthResponse() {}

    // Constructor with token, username, and role
    public AuthResponse(String token, String username, String role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Constructor with message only
    public AuthResponse(String message) {
        this.message = message;
    }

    // Constructor with all fields (message, token, username, role)
    public AuthResponse(String message, String token, String username, String role) {
        this.message = message;
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getters and Setters
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
}
