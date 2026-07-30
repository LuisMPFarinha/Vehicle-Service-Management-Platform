package com.example.vehicleservice.application.dto;

import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;

import java.time.Instant;
import java.util.UUID;

public record ServiceRequestResponse(
        UUID id,
        UUID vehicleId,
        String description,
        Priority priority,
        ServiceRequestStatus status,
        String assignedTechnician,
        Instant createdAt,
        Instant completedAt
) {
}
