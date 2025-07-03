package com.aiims.gdms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aiims.gdms.entity.MealMaster;
import com.aiims.gdms.entity.MealMaster.MealType;

@Repository
public interface MealMasterRepository extends JpaRepository<MealMaster, Long> {
	
	 List<MealMaster> findByMealType(MealType mealType);
     
}
