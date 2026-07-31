package com.example.vehicleservice.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

import com.example.vehicleservice.domain.model.Vehicle;

@Entity
@Table(name = "vehicles")
public class VehicleEntity {

    @Id
    private UUID id;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private String model;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    protected VehicleEntity() {
    }

    private VehicleEntity(UUID id, String registrationNumber, String model, String ownerName) {
        this.id = id;
        this.registrationNumber = registrationNumber;
        this.model = model;
        this.ownerName = ownerName;
    }

    static VehicleEntity fromDomain(Vehicle vehicle) {
        return new VehicleEntity(
                vehicle.getId(),
                vehicle.getRegistrationNumber(),
                vehicle.getModel(),
                vehicle.getOwnerName());
    }

    Vehicle toDomain() {
        return Vehicle.restore(
                id,
                registrationNumber,
                model,
                ownerName);
    }
}
