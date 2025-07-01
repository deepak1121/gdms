package com.aiims.gdms.entity;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;


@Entity
@Table(name = "patient_profiles")
public class PatientProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @NotBlank
    @Column(nullable = false)
    private String firstName;
    
    @NotBlank
    @Column(nullable = false)
    private String lastName;
    
    
    @Column
    private Long phoneNumber;
    
    
    @Column
    private Integer birthYear;
    
    @Column
    private Integer gravida;
    
    @Column
    private Integer para;
    
    @Column
    private Integer livingChildren;
    
    @Column
    private Integer abortions;
    
    @Column
    private Integer lastMenstrualPeriod;
    
    @Column
    private String photoPath;

    
    // Constructors
    public PatientProfile() {}
    
    public PatientProfile(User user, String firstName, String lastName) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    
    public PatientProfile(User user, String firstName, String lastName, Long phoneNumber,
		    Integer birthYear, Integer gravida, Integer para, Integer livingChildren,
			Integer abortions, Integer lastMenstrualPeriod) {
		this.user = user;
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.birthYear = birthYear;
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
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
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
	
	public String getPhotoPath() {
	    return photoPath;
	}

	public void setPhotoPath(String photoPath) {
	    this.photoPath = photoPath;
	}


} 