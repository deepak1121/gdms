package com.aiims.gdms.repository;

import com.aiims.gdms.entity.GlucoseLog;
import com.aiims.gdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface GlucoseLogRepository extends JpaRepository<GlucoseLog, Long> {
    
    List<GlucoseLog> findByPatientOrderByTimestampDesc(User patient);
    
    @Query("SELECT g FROM GlucoseLog g WHERE g.patient = :patient AND DATE(g.timestamp) = :date")
    List<GlucoseLog> findByPatientAndDate(@Param("patient") User patient, @Param("date") LocalDate date);
    
    @Query("SELECT g FROM GlucoseLog g WHERE g.patient = :patient AND g.timestamp >= :startDate AND g.timestamp <= :endDate ORDER BY g.timestamp DESC")
    List<GlucoseLog> findByPatientAndDateRange(@Param("patient") User patient, 
                                               @Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(g) FROM GlucoseLog g WHERE g.patient = :patient AND DATE(g.timestamp) = :date")
    long countByPatientAndDate(@Param("patient") User patient, @Param("date") LocalDate date);

    List<GlucoseLog> findByPatient(User user);
} 