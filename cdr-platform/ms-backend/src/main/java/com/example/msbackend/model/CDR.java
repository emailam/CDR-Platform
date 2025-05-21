package com.example.msbackend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "cdrs")
public class CDR {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private String source;
    private String destination;
    private LocalDateTime startTime;
    @Enumerated(EnumType.STRING)
    private ServiceType service;

    @Column(name = "data_usage")
    private Float usage;
    private LocalDateTime updatedAt;
    private LocalDateTime createdAt;
    private String fileName;

    public CDR() {

    }

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
