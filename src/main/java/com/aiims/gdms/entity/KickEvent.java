package com.aiims.gdms.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "kick_events")
public class KickEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "session_id", nullable = false)
    @JsonBackReference
    private KickSession session;

    @Column(nullable = false)
    private LocalDateTime timestamp;

    // Constructors
    public KickEvent() {}
    public KickEvent(KickSession session, LocalDateTime timestamp) {
        this.session = session;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public KickSession getSession() { return session; }
    public void setSession(KickSession session) { this.session = session; }
    public LocalDateTime getTimestamp() { return timestamp; }
    public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
} 