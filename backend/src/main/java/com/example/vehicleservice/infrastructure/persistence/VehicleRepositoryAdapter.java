package com.example.vehicleservice.infrastructure.persistence;

import com.example.vehicleservice.domain.model.Vehicle;
import com.example.vehicleservice.domain.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class VehicleRepositoryAdapter implements VehicleRepository {
    private final SpringDataVehicleJpaRepository jpaRepository;

    @Override
    public Vehicle save(Vehicle vehicle) {
        VehicleEntity entity = VehicleEntity.fromDomain(vehicle);
        VehicleEntity savedEntity = jpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public Optional<Vehicle> findById(UUID id) {
        return jpaRepository.findById(id).map(VehicleEntity::toDomain);
    }

    @Override
    public Optional<Vehicle> findByRegistrationNumber(String registrationNumber) {
        return jpaRepository.findByRegistrationNumber(registrationNumber).map(VehicleEntity::toDomain);
    }
}
