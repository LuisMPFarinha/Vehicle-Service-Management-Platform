package com.example.vehicleservice.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "vehicles")
public class JpaVehicleEntity {

    @Id
    private UUID id;

    @Column(name = "registration_number", nullable = false, unique = true)
    private String registrationNumber;

    @Column(nullable = false)
    private String model;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    protected JpaVehicleEntity() {
    }

    // TODO: Add mapping methods when implementing persistence.
}
