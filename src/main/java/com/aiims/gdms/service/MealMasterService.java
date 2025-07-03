package com.aiims.gdms.service;


import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.aiims.gdms.dto.MealTypeRequest;
import com.aiims.gdms.entity.MealMaster;
import com.aiims.gdms.entity.MealMaster.MealType;
import com.aiims.gdms.repository.MealMasterRepository;

import io.jsonwebtoken.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
@Service
public class MealMasterService {

    @Autowired
    private MealMasterRepository mealMasterRepository;

    public MealMaster addMeal(
            String name, String description, String hindiName, String hindiDescription,
            String mealType, Double carbs, Double sugar, Double fiber, boolean doctorRecommended,
            MultipartFile mealImage) throws IOException, java.io.IOException {

        String imagePath = null;

        if (mealImage != null && !mealImage.isEmpty()) {
            String fileName = UUID.randomUUID() + "_" + mealImage.getOriginalFilename().replaceAll("\\s+", "_");
            Path uploadPath = Paths.get("src/main/resources/static/MealPhotos");

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(mealImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            imagePath = "MealPhotos/" + fileName;
        }

        MealMaster meal = new MealMaster();
        meal.setName(name);
        meal.setDescription(description);
        meal.setHindiName(hindiName);
        meal.setHindiDescription(hindiDescription);
        meal.setMealType(MealMaster.MealType.valueOf(mealType.trim().toUpperCase()));
        meal.setCarbs(carbs);
        meal.setSugar(sugar);
        meal.setFiber(fiber);
        meal.setDoctorRecommended(doctorRecommended);
        meal.setMealImage(imagePath);

        return mealMasterRepository.save(meal);
    }



    public List<MealMaster> getAllMeal(){
        List<MealMaster> allMeals = mealMasterRepository.findAll(); 
        return allMeals;
 }
    
    
    public List<MealMaster> getMealsByType(MealTypeRequest request) {
        MealType mealType;
        try {
            mealType = MealType.valueOf(request.getMealType().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid meal type: " + request.getMealType());
        }

        return mealMasterRepository.findAll()
                .stream()
                .filter(meal -> meal.getMealType() == mealType)
                .collect(Collectors.toList());
    }
    

}
