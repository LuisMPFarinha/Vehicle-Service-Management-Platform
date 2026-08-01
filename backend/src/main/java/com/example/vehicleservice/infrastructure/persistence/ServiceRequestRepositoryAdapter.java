package com.example.vehicleservice.infrastructure.persistence;

import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import com.example.vehicleservice.domain.repository.ServiceRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ServiceRequestRepositoryAdapter implements ServiceRequestRepository {
    private final SpringDataServiceRequestJpaRepository jpaRepository;

    @Override
    public ServiceRequest save(ServiceRequest serviceRequest) {
        ServiceRequestEntity serviceRequestEntity = ServiceRequestEntity.fromDomain(serviceRequest);
        ServiceRequestEntity savedEntity = jpaRepository.save(serviceRequestEntity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<ServiceRequest> findById(UUID id) {
        // TODO: Load entity and map to domain model.
        return Optional.empty();
    }

    @Override
    public boolean existsActiveDuplicate(UUID vehicleId, String description) {
        return jpaRepository.existsByVehicleIdAndDescriptionAndStatusIn(
            vehicleId,
            description,
            List.of(ServiceRequestStatus.OPEN, ServiceRequestStatus.IN_PROGRESS, ServiceRequestStatus.WAITING_FOR_PARTS)
        );
    }
}
