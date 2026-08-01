package com.example.vehicleservice.infrastructure.persistence;

import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "service_requests")
public class ServiceRequestEntity {

    @Id
    private UUID id;

    @Column(name = "vehicle_id", nullable = false)
    private UUID vehicleId;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Priority priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceRequestStatus status;

    @Column(name = "assigned_technician")
    private String assignedTechnician;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "completed_at")
    private Instant completedAt;

    protected ServiceRequestEntity() {
    }

    private ServiceRequestEntity(UUID id, UUID vehicleId, String description, Priority priority, ServiceRequestStatus status, String assignedTechnician, Instant createdAt, Instant completedAt) {
        this.id = id;
        this.vehicleId = vehicleId;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.assignedTechnician = assignedTechnician;
        this.createdAt = createdAt;
        this.completedAt = completedAt;
    }

    static ServiceRequestEntity fromDomain(ServiceRequest serviceRequest) {
        return new ServiceRequestEntity(
            serviceRequest.getId(),
            serviceRequest.getVehicleId(),
            serviceRequest.getDescription(),
            serviceRequest.getPriority(),
            serviceRequest.getStatus(),
            serviceRequest.getAssignedTechnician(),
            serviceRequest.getCreatedAt(),
            serviceRequest.getCompletedAt()
        );
    }

    ServiceRequest toDomain() {
        return ServiceRequest.restore(
            id,
            vehicleId,
            description,
            priority,
            status,
            assignedTechnician,
            createdAt,
            completedAt
        );
    }
}
