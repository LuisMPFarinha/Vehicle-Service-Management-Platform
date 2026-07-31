package com.example.vehicleservice.application.exception;

public class VehicleAlreadyExistsException extends RuntimeException {

    public VehicleAlreadyExistsException(String registrationNumber) {
        super("Vehicle with registration number " + registrationNumber + " already exists");
    }
}
