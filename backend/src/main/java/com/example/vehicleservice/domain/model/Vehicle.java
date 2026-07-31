package com.example.vehicleservice.domain.model;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.AccessLevel;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Vehicle {
    private final UUID id;
    private String registrationNumber;
    private String model;
    private String ownerName;

    public static Vehicle create(String registrationNumber, String model, String ownerName) {
        if (isBlank(registrationNumber))
            throw new IllegalArgumentException("Registration number is required");
        if (isBlank(model))
            throw new IllegalArgumentException("Model is required");
        if (isBlank(ownerName))
            throw new IllegalArgumentException("Owner name is required");

        return new Vehicle(UUID.randomUUID(), registrationNumber, model, ownerName);
    }

    public static Vehicle restore(UUID id, String registrationNumber, String model, String ownerName) {
        return new Vehicle(id, registrationNumber, model, ownerName);
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
