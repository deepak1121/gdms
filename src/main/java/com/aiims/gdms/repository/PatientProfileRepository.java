package com.aiims.gdms.repository;

import com.aiims.gdms.entity.PatientProfile;
import com.aiims.gdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientProfileRepository extends JpaRepository<PatientProfile, Long> {
    
    Optional<PatientProfile> findByUser(User user);
    
    Optional<PatientProfile> findByUserId(Long userId);
} 