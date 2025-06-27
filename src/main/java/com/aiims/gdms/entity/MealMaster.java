package com.aiims.gdms.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class MealMaster {

    @Id 
    @GeneratedValue
    private Long id;

    private String name;
    private String description;

    private String hindiName;
    private String hindiDescription;

    @Enumerated(EnumType.STRING)
    private MealType mealType;
    
    @Lob
    private String mealImage;

    private Double carbs;
    private Double sugar;
    private Double fiber;

    private boolean doctorRecommended;

    public enum MealType {
        BREAKFAST, LUNCH, DINNER, SNACK
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public Double getSugar() {
        return sugar;
    }

    public void setSugar(Double sugar) {
        this.sugar = sugar;
    }

    public Double getFiber() {
        return fiber;
    }

    public void setFiber(Double fiber) {
        this.fiber = fiber;
    }

    public boolean isDoctorRecommended() {
        return doctorRecommended;
    }

    public void setDoctorRecommended(boolean doctorRecommended) {
        this.doctorRecommended = doctorRecommended;
    }
     
    public String getHindiName() {
        return hindiName;
    }

    public void setHindiName(String hindiName) {
        this.hindiName = hindiName;
    }   

    public String getHindiDescription() {
        return hindiDescription;
    }

    public void setHindiDescription(String hindiDescription) {
        this.hindiDescription = hindiDescription;
    }

    public MealType getMealType() {
        return mealType;
    }

    public void setMealType(MealType mealType) {
        this.mealType = mealType;
    }

	public String getMealImage() {
		return mealImage;
	}

	public void setMealImage(String mealImage) {
		this.mealImage = mealImage;
	}
    
    

}
