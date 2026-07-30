package com.example.vehicleservice.infrastructure.persistence;

import com.example.vehicleservice.domain.model.Vehicle;
import com.example.vehicleservice.domain.repository.VehicleRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaVehicleRepositoryAdapter implements VehicleRepository {

    @Override
    public Vehicle save(Vehicle vehicle) {
        // TODO: Map domain model to JPA entity and persist.
        return vehicle;
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        // TODO: Load entity and map to domain model.
        return Optional.empty();
    }

    @Override
    public Optional<Vehicle> findByRegistrationNumber(String registrationNumber) {
        // TODO: Load entity and map to domain model.
        return Optional.empty();
    }
}
