package com.aiims.gdms.repository;

import com.aiims.gdms.entity.DoctorPatientMapping;
import com.aiims.gdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorPatientMappingRepository extends JpaRepository<DoctorPatientMapping, Long> {
    
    List<DoctorPatientMapping> findByDoctorAndActiveTrue(User doctor);
    
    List<DoctorPatientMapping> findByPatientAndActiveTrue(User patient);
    
    Optional<DoctorPatientMapping> findByDoctorAndPatientAndActiveTrue(User doctor, User patient);
    
    boolean existsByDoctorAndPatientAndActiveTrue(User doctor, User patient);
} 