package com.aiims.gdms.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class AuthRequest {

	@NotBlank
	@Size(min = 10, max = 50)
	@Pattern(regexp = "(^[6-9][0-9]{9}$)|(^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$)", message = "Username must be a valid email or a 10-digit phone number starting with 6, 7, 8, or 9")
	private String username;

	@NotBlank
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%&!]).{8,}$", message = "Password must be at least 8 characters and contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
	private String password;

	private String role;

	// Additional fields for registration
	@NotNull(message = "Phone number is required")
	private Long phoneNumber;

	@NotBlank
	private String firstName;

	@NotBlank
	private String lastName;

	@NotNull(message = "Birth year is required")
	private Integer birthYear;

	private Integer gravida;
	private Integer para;
	private Integer livingChildren;
	private Integer abortions;
	private Integer lastMenstrualPeriod;
	private MultipartFile photo;

	// Constructors
	public AuthRequest() {
	}

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

	public MultipartFile getPhoto() {
		return photo;
	}

	public void setPhoto(MultipartFile photo) {
		this.photo = photo;
	}

}
