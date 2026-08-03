package com.example.vehicleservice.domain.model;

import com.example.vehicleservice.domain.exception.DomainRuleViolationException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceRequest {
    private UUID id;
    private UUID vehicleId;
    private String description;
    private Priority priority;
    private ServiceRequestStatus status;
    private String assignedTechnician;
    private Instant createdAt;
    private Instant completedAt;

    public static ServiceRequest create(
        UUID vehicleId,
        String description,
        Priority priority) {

        if (ValidationUtils.isBlank(vehicleId))
            throw new IllegalArgumentException("vehicleId is required");
        if (ValidationUtils.isBlank(description))
            throw new IllegalArgumentException("description is required");
        if (ValidationUtils.isBlank(priority))
            throw new IllegalArgumentException("priority is required");

        return new ServiceRequest(UUID.randomUUID(), vehicleId, description, priority, ServiceRequestStatus.OPEN, null, Instant.now(), null);
    }

    public static ServiceRequest restore(UUID id, UUID vehicleId, String description, Priority priority, ServiceRequestStatus status, String assignedTechnician, Instant createdAt, Instant completedAt) {
        return new ServiceRequest(id, vehicleId, description, priority, status, assignedTechnician, createdAt, completedAt);
    }

    public boolean isActive() {
        return this.status == ServiceRequestStatus.OPEN || this.status == ServiceRequestStatus.IN_PROGRESS || this.status == ServiceRequestStatus.WAITING_FOR_PARTS;
    }

    public void assignTechnician(String technician) {
        if(!this.isActive()) throw new DomainRuleViolationException("Only active service requests can be assigned");
        this.assignedTechnician = technician;
    }

    public void updateStatus(ServiceRequestStatus status) {
        this.status = status;
    }
}
