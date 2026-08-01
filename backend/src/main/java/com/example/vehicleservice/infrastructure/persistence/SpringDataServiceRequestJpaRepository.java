package com.example.vehicleservice.infrastructure.persistence;

import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.UUID;

public interface SpringDataServiceRequestJpaRepository extends JpaRepository<ServiceRequestEntity, UUID> {
    boolean existsByVehicleIdAndDescriptionAndStatusIn(UUID vehicleId, String description, Collection<ServiceRequestStatus> statuses);
}
