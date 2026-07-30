package com.example.vehicleservice.domain.model;

import java.time.Instant;
import java.util.UUID;

public record ServiceRequest(
        UUID id,
        UUID vehicleId,
        String description,
        Priority priority,
        ServiceRequestStatus status,
        String assignedTechnician,
        Instant createdAt,
        Instant completedAt
) {
    public boolean isActive() {
        // TODO: Implement active-state rules during the service-request exercises.
        return false;
    }
}
