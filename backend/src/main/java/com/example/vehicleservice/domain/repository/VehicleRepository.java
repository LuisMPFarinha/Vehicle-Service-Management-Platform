package com.example.vehicleservice.domain.repository;

import com.example.vehicleservice.domain.model.Vehicle;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository {

    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(UUID id);

    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);
}
