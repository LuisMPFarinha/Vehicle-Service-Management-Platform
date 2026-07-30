package com.example.vehicleservice.domain.repository;

import com.example.vehicleservice.domain.model.ServiceRequest;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRequestRepository {

    ServiceRequest save(ServiceRequest serviceRequest);

    Optional<ServiceRequest> findById(UUID id);

    boolean existsActiveDuplicate(UUID vehicleId, String description);
}
