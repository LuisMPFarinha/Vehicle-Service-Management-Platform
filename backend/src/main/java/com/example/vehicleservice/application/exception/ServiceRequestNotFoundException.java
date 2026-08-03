package com.example.vehicleservice.application.exception;

import java.util.UUID;

public class ServiceRequestNotFoundException extends RuntimeException {
    public ServiceRequestNotFoundException(UUID serviceRequestId) {
        super("VehicleId " + serviceRequestId + " not found");
    }
}
