package com.aiims.gdms.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.aiims.gdms.entity.MealMaster;
import com.aiims.gdms.service.MealMasterService;


@RestController
@RequestMapping("/api/meal-master")
@CrossOrigin(origins = "*")
public class MealMasterController {
    @Autowired
    private  MealMasterService mealMasterService;

    @PostMapping("/add")
    public ResponseEntity<Object> create(@RequestBody MealMaster request) {
        try {
            return new ResponseEntity<Object>(mealMasterService.addMeal(request), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<Object>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
