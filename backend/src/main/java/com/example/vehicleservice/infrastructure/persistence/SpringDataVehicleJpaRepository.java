package com.example.vehicleservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataVehicleJpaRepository extends JpaRepository<VehicleEntity, UUID> {

    Optional<VehicleEntity> findByRegistrationNumber(String registrationNumber);
}
