package com.aiims.gdms.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AuthRequest {
    
	@NotBlank
	@Size(min = 8, max = 12)
	@Pattern(regexp = "^[A-Za-z0-9@#]{8,12}$", message = "Username must be 8-12 characters long and can contain letters, numbers, @ and # only.")
	private String username;

    
    @NotBlank
    private String password;
    
    private String role;
    
    // Additional fields for registration
    private Long phoneNumber;
    private String firstName;
    private String lastName;
    private Integer birthYear;
    private Integer gravida;
    private Integer para;
    private Integer livingChildren;
    private Integer abortions;
    private Integer lastMenstrualPeriod;
   

	// Constructors
    public AuthRequest() {}
    
    public AuthRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    // Getters and Setters
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }

	public Integer getBirthYear() {
		return birthYear;
	}

	public void setBirthYear(Integer birthYear) {
		this.birthYear = birthYear;
	}

	public Integer getGravida() {
		return gravida;
	}

	public void setGravida(Integer gravida) {
		this.gravida = gravida;
	}

	public Integer getPara() {
		return para;
	}

	public void setPara(Integer para) {
		this.para = para;
	}

	public Integer getLivingChildren() {
		return livingChildren;
	}

	public void setLivingChildren(Integer livingChildren) {
		this.livingChildren = livingChildren;
	}

	public Integer getAbortions() {
		return abortions;
	}

	public void setAbortions(Integer abortions) {
		this.abortions = abortions;
	}

	public Integer getLastMenstrualPeriod() {
		return lastMenstrualPeriod;
	}

	public void setLastMenstrualPeriod(Integer lastMenstrualPeriod) {
		this.lastMenstrualPeriod = lastMenstrualPeriod;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Long getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(Long phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	
    
    
    
   
    
} 