package com.example.msloader.model;

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
    private Float usage;

    private LocalDateTime createdAt;
    private String fileName;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}