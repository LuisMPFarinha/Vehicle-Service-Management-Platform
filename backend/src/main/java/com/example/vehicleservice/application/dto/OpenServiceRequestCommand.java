package com.example.vehicleservice.application.dto;

import com.example.vehicleservice.domain.model.Priority;

import java.util.UUID;

public record OpenServiceRequestCommand(
        UUID vehicleId,
        String description,
        Priority priority
) {
}
