package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.CreateVehicleCommand;
import com.example.vehicleservice.application.dto.VehicleResponse;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PlaceholderVehicleApplicationService implements VehicleApplicationService {

    @Override
    public VehicleResponse createVehicle(CreateVehicleCommand command) {
        // TODO: Implement validation, domain creation, and persistence test-first.
        return new VehicleResponse(UUID.randomUUID(), command.registrationNumber(), command.model(), command.ownerName());
    }
}
