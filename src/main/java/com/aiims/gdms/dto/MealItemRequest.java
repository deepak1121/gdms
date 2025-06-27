package com.aiims.gdms.dto;

public class MealItemRequest {

    private Long mealMasterId;
    private String customMealName; 
    private double quantity;
    private String unit;
    
    public Long getMealMasterId() {
        return mealMasterId;
    }
    public void setMealMasterId(Long mealMasterId) {
        this.mealMasterId = mealMasterId;
    }
    public String getCustomMealName() {
        return customMealName;
    }
    public void setCustomMealName(String customMealName) {
        this.customMealName = customMealName;
    }
    public double getQuantity() {
        return quantity;
    }
    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }



}
