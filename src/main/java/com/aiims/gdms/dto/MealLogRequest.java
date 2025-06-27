package com.aiims.gdms.dto;

import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public class MealLogRequest {
    
    @NotNull(message = "Meal type is required")
    private String mealType;
    
    @Positive(message = "Estimated carbohydrates must be positive")
    private Integer estimatedCarbohydrates;
    
    private String notes;

    private List<MealItemRequest> mealItems;
    
    
    public MealLogRequest() {}
    
    public MealLogRequest(String mealType, Integer estimatedCarbohydrates, String notes) {
        this.mealType = mealType;
        this.estimatedCarbohydrates = estimatedCarbohydrates;
        this.notes = notes;
    }

    
    public String getMealType() {
        return mealType;
    }
    
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }
    
    public Integer getEstimatedCarbohydrates() {
        return estimatedCarbohydrates;
    }
    
    public void setEstimatedCarbohydrates(Integer estimatedCarbohydrates) {
        this.estimatedCarbohydrates = estimatedCarbohydrates;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<MealItemRequest> getMealItems() {
        return mealItems;
    }

    public void setMealItems(List<MealItemRequest> mealItems) {
        this.mealItems = mealItems;
    }
    


} 