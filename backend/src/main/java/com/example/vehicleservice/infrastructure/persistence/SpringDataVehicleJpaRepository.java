package com.example.vehicleservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataVehicleJpaRepository extends JpaRepository<JpaVehicleEntity, UUID> {

    Optional<JpaVehicleEntity> findByRegistrationNumber(String registrationNumber);
}
