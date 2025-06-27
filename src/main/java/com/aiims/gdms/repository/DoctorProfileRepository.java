package com.aiims.gdms.repository;

import com.aiims.gdms.entity.DoctorProfile;
import com.aiims.gdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorProfileRepository extends JpaRepository<DoctorProfile, Long> {
    
    Optional<DoctorProfile> findByUser(User user);
    
    Optional<DoctorProfile> findByUserId(Long userId);
} 