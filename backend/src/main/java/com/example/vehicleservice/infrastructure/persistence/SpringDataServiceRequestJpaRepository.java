package com.example.vehicleservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataServiceRequestJpaRepository extends JpaRepository<ServiceRequestEntity, UUID> {

    // TODO: Add query methods during filtering and duplicate-prevention exercises.
}
