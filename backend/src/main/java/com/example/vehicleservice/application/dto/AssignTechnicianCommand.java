package com.example.vehicleservice.application.dto;

import java.util.UUID;

public record AssignTechnicianCommand(
        UUID serviceRequestId,
        String technicianName
) {
}
