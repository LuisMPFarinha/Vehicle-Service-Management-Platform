package com.example.vehicleservice.domain.model;

import java.util.UUID;

public record Vehicle(
        UUID id,
        String registrationNumber,
        String model,
        String ownerName
) {
    public static Vehicle placeholder(UUID id, String registrationNumber, String model, String ownerName) {
        // TODO: Replace this factory with validation during the create-vehicle exercise.
        return new Vehicle(id, registrationNumber, model, ownerName);
    }
}
