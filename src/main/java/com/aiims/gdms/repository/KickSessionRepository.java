package com.aiims.gdms.repository;

import com.aiims.gdms.entity.KickSession;
import com.aiims.gdms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;


@Repository
public interface KickSessionRepository extends JpaRepository<KickSession, Long> {
    @Query("SELECT s FROM KickSession s WHERE s.patient = :patient AND s.completed = false AND s.startTime >= :windowStart ORDER BY s.startTime DESC LIMIT 1")
    Optional<KickSession> findActiveSession(@Param("patient") User patient, @Param("windowStart") LocalDateTime windowStart);
    
    List<KickSession> findByPatientAndCompletedTrueOrderByStartTimeDesc(User patient);

    List<KickSession> findByPatientOrderByStartTimeDesc(User patient);

} 