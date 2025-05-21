package com.example.msbackend.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class CDRDTO {
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // if user sends an id, it will be ignored
    private UUID id;
    @NotBlank(message = "Source is required")
    private String source;
    @NotBlank(message = "Destination is required")
    private String destination;
    @NotNull(message = "Start time is required")
    private LocalDateTime startTime;
    @NotNull(message = "Service type is required")
    private ServiceType service;
    @NotNull(message = "Usage is required")
    @Positive(message = "Usage must be positive")
    private Float usage;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // if user sends an update time, it will be ignored.
    private LocalDateTime updatedAt;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY) // if user sends a create time, it will be ignored.
    private LocalDateTime createdAt;
    @NotNull
    private String fileName;
}
