package com.aiims.gdms.dto;

import jakarta.validation.constraints.NotBlank;

public class MealTypeRequest {

    @NotBlank(message = "Meal type is required")
    private String mealType;

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
}