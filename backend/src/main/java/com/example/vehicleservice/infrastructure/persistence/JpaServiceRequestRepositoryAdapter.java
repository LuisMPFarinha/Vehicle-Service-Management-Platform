package com.example.vehicleservice.infrastructure.persistence;

import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.repository.ServiceRequestRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaServiceRequestRepositoryAdapter implements ServiceRequestRepository {

    @Override
    public ServiceRequest save(ServiceRequest serviceRequest) {
        // TODO: Map domain model to JPA entity and persist.
        return serviceRequest;
    }

    @Override
    public Optional<ServiceRequest> findById(UUID id) {
        // TODO: Load entity and map to domain model.
        return Optional.empty();
    }

    @Override
    public boolean existsActiveDuplicate(UUID vehicleId, String description) {
        // TODO: Implement duplicate-active-request query.
        return false;
    }
}
