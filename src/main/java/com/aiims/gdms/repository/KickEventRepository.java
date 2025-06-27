package com.aiims.gdms.repository;

import com.aiims.gdms.entity.KickEvent;
import com.aiims.gdms.entity.KickSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KickEventRepository extends JpaRepository<KickEvent, Long> {
    List<KickEvent> findBySession(KickSession session);
} 