package com.example.vehicleservice.domain.repository;

import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface ServiceRequestRepository {

    ServiceRequest save(ServiceRequest serviceRequest);

    Optional<ServiceRequest> findById(UUID id);

    boolean existsActiveDuplicate(UUID vehicleId, String description);

    Page<ServiceRequest> findFiltered(ServiceRequestStatus status, Priority priority, String regNum, Pageable pageable);
}
