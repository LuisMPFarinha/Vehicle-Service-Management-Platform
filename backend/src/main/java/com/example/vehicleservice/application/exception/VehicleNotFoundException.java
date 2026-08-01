package com.example.vehicleservice.application.exception;

import java.util.UUID;

public class VehicleNotFoundException extends RuntimeException {
    public VehicleNotFoundException(UUID vehicleId) {
        super("VehicleId " + vehicleId + " not found");
    }
}
