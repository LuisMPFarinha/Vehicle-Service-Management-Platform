package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.CreateVehicleCommand;
import com.example.vehicleservice.application.dto.VehicleResponse;
import com.example.vehicleservice.application.exception.VehicleAlreadyExistsException;
import com.example.vehicleservice.domain.model.Vehicle;
import com.example.vehicleservice.domain.repository.VehicleRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultVehicleService implements VehicleService {
    private final VehicleRepository vehicleRepository;

    @Override
    public VehicleResponse createVehicle(CreateVehicleCommand command) {
        vehicleRepository.findByRegistrationNumber(command.registrationNumber())
                .ifPresent(existingVehicle -> {
                    throw new VehicleAlreadyExistsException(command.registrationNumber());
                });

        Vehicle vehicle = Vehicle.create(
                command.registrationNumber(),
                command.model(),
                command.ownerName());

        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        return new VehicleResponse(
                savedVehicle.getId(),
                savedVehicle.getRegistrationNumber(),
                savedVehicle.getModel(),
                savedVehicle.getOwnerName());
    }
}
