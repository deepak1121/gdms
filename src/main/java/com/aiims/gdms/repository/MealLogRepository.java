package com.aiims.gdms.repository;

import com.aiims.gdms.entity.MealLog;
import com.aiims.gdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MealLogRepository extends JpaRepository<MealLog, Long> {
    
    List<MealLog> findByPatientOrderByTimestampDesc(User patient);
    
    @Query("SELECT m FROM MealLog m WHERE m.patient = :patient AND DATE(m.timestamp) = :date")
    List<MealLog> findByPatientAndDate(@Param("patient") User patient, @Param("date") LocalDate date);
    
    @Query("SELECT m FROM MealLog m WHERE m.patient = :patient AND m.timestamp >= :startDate AND m.timestamp <= :endDate ORDER BY m.timestamp DESC")
    List<MealLog> findByPatientAndDateRange(@Param("patient") User patient, 
                                            @Param("startDate") LocalDateTime startDate, 
                                            @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(m) FROM MealLog m WHERE m.patient = :patient AND DATE(m.timestamp) = :date")
    long countByPatientAndDate(@Param("patient") User patient, @Param("date") LocalDate date);

    List<MealLog> findByPatient(User user);
    
    List<MealLog> findByPatientAndTimestampBetweenOrderByTimestampDesc(User patient, LocalDateTime start, LocalDateTime end);
} 