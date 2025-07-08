package com.aiims.gdms.dto;

public class MealItemDto {

    private String name;
    private Double quantity;
    private String unit;
    private String mealType;
    private Double carbs;
    private boolean doctorRecommended;
    private String mealImage; 

    public MealItemDto(String name, Double quantity, String unit, String mealType, Double carbs, boolean doctorRecommended, String mealImage) {
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.mealType = mealType;
        this.carbs = carbs;
        this.doctorRecommended = doctorRecommended;
        this.mealImage = mealImage;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public Double getCarbs() {
        return carbs;
    }

    public void setCarbs(Double carbs) {
        this.carbs = carbs;
    }

    public boolean isDoctorRecommended() {
        return doctorRecommended;
    }

    public void setDoctorRecommended(boolean doctorRecommended) {
        this.doctorRecommended = doctorRecommended;
    }

    public String getMealImage() {
        return mealImage;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }
}
