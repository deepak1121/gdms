package com.aiims.gdms.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.aiims.gdms.entity.MealMaster;
import com.aiims.gdms.repository.MealMasterRepository;

@Service
public class MealMasterService {

    @Autowired
    private MealMasterRepository mealMasterRepository;

    public MealMaster addMeal(MealMaster meal){
           return mealMasterRepository.save(meal);
    }

    public List<MealMaster> getAllMeal(){
        List<MealMaster> allMeals = mealMasterRepository.findAll(); 
        return allMeals;
 }
    

}
