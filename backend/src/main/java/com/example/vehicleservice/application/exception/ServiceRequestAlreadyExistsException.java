package com.example.vehicleservice.application.exception;

import java.util.UUID;

public class ServiceRequestAlreadyExistsException extends RuntimeException {

    public ServiceRequestAlreadyExistsException(UUID vehicleId, String description) {
        super("Service request with vehicleId " + vehicleId + " and description " + description + " already exists");
    }
}
