package com.example.vehicleservice.application.dto;

import java.util.UUID;

public record VehicleResponse(
        UUID id,
        String registrationNumber,
        String model,
        String ownerName
) {
}
