package com.aiims.gdms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aiims.gdms.entity.MealMaster;
import com.aiims.gdms.service.MealMasterService;


@RestController
@RequestMapping("/api/meal-master")
@CrossOrigin(origins = "*")
public class MealMasterController {
    @Autowired
    private  MealMasterService mealMasterService;

    @PostMapping(value = "/add", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMeal(
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String hindiName,
            @RequestParam String hindiDescription,
            @RequestParam String mealType,
            @RequestParam Double carbs,
            @RequestParam Double sugar,
            @RequestParam Double fiber,
            @RequestParam boolean doctorRecommended,
            @RequestPart(required = false) MultipartFile mealImage) {

        try {
            MealMaster savedMeal = mealMasterService.addMeal(
                    name, description, hindiName, hindiDescription,
                    mealType, carbs, sugar, fiber, doctorRecommended, mealImage
            );
            return ResponseEntity.ok(savedMeal);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


    
    @GetMapping("/get")
    public ResponseEntity<Object> get() {
        try {
            return new ResponseEntity<Object>(mealMasterService.getAllMeal(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    


}
