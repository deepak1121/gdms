package com.aiims.gdms.dto;

import java.util.List;

public class MealLogDto {

    private String mealType;
    private List<MealItemDto> mealItems;

    public MealLogDto(String mealType, List<MealItemDto> mealItems) {
        this.mealType = mealType;
        this.mealItems = mealItems;
    }
    
    

	public MealLogDto() {
		super();
		// TODO Auto-generated constructor stub
	}



	public String getMealType() {
		return mealType;
	}

	public void setMealType(String mealType) {
		this.mealType = mealType;
	}

	public List<MealItemDto> getMealItems() {
		return mealItems;
	}

	public void setMealItems(List<MealItemDto> mealItems) {
		this.mealItems = mealItems;
	}

    // Getters and Setters
    
}
