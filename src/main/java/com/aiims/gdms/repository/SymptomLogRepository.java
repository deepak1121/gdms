package com.aiims.gdms.repository;

import com.aiims.gdms.entity.SymptomLog;
import com.aiims.gdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SymptomLogRepository extends JpaRepository<SymptomLog, Long> {
    
    List<SymptomLog> findByPatientOrderByTimestampDesc(User patient);
    
    @Query("SELECT s FROM SymptomLog s WHERE s.patient = :patient AND DATE(s.timestamp) = :date")
    List<SymptomLog> findByPatientAndDate(@Param("patient") User patient, @Param("date") LocalDate date);
    
    List<SymptomLog> findByPatient(User patient);

    @Query("SELECT s FROM SymptomLog s WHERE s.patient = :patient AND s.timestamp >= :startDate AND s.timestamp <= :endDate ORDER BY s.timestamp DESC")
    List<SymptomLog> findByPatientAndDateRange(@Param("patient") User patient, 
                                               @Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(s) FROM SymptomLog s WHERE s.patient = :patient AND DATE(s.timestamp) = :date")
    long countByPatientAndDate(@Param("patient") User patient, @Param("date") LocalDate date);
} 