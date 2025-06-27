package com.aiims.gdms.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class MealItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JsonBackReference
    private MealLog mealLog;
    
    @ManyToOne
    private MealMaster mealMaster;
    private String customMealName;  
    private Double quantity; 
    private String unit;
   

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public MealLog getMealLog() {
        return mealLog;
    }
    public void setMealLog(MealLog mealLog) {
        this.mealLog = mealLog;
    }
    public MealMaster getMealMaster() {
        return mealMaster;
    }
    public void setMealMaster(MealMaster mealMaster) {
        this.mealMaster = mealMaster;
    }
    public String getCustomMealName() {
        return customMealName;
    }
    public void setCustomMealName(String customMealName) {
        this.customMealName = customMealName;
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
    
    

}
