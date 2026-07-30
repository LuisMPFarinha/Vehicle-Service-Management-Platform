package com.example.vehicleservice.application.dto;

public record CreateVehicleCommand(
        String registrationNumber,
        String model,
        String ownerName
) {
}
