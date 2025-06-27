package com.aiims.gdms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aiims.gdms.entity.MealMaster;

@Repository
public interface MealMasterRepository extends JpaRepository<MealMaster, Long> {
     
}
