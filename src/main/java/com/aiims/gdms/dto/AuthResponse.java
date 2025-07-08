package com.aiims.gdms.dto;

public class AuthResponse {
    
    private String token;
    private String username;
    private String role;
    private String message;
    private Long Id;
    private String photoPath;
    
    // Constructors
    public AuthResponse() {}

    // Constructor with token, username, and role
    public AuthResponse(String token, String username, String role, Long Id, String photoPath) {
        this.token = token;
        this.username = username;
        this.role = role;
        this.setId(Id);
        this.photoPath = photoPath;
    }

    // Constructor with message only
    public AuthResponse(String message) {
        this.message = message;
    }

    // Constructor with all fields (message, token, username, role)
    public AuthResponse(String message, String token, String username, String role, Long Id, String photoPath) {
        this.message = message;
        this.token = token;
        this.username = username;
        this.role = role;
        this.setId(Id);
        this.photoPath = photoPath;
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

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getPhotoPath() {
		return photoPath;
	}

	public void setPhotoPath(String photoPath) {
		this.photoPath = photoPath;
	}
	
    
    
}
