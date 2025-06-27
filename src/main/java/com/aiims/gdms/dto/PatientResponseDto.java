package com.aiims.gdms.dto;



public class PatientResponseDto {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private Integer birthYear;
    private Integer gravida;
    private Integer para;
    private Integer livingChildren;
    private Integer abortions;
    private Integer lastMenstrualPeriod;


    // Constructors
    public PatientResponseDto() {
    }

    public PatientResponseDto(Long id, String firstName, String lastName, Long phoneNumber, Integer birthYear,Integer gravida, Integer para, Integer livingChildren,
			Integer abortions, Integer lastMenstrualPeriod) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthYear=birthYear;
        this.gravida = gravida;
		this.para = para;
		this.livingChildren = livingChildren;
		this.abortions = abortions;
		this.lastMenstrualPeriod = lastMenstrualPeriod;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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


} 