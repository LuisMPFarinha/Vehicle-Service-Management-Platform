package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.CreateVehicleCommand;
import com.example.vehicleservice.application.dto.VehicleResponse;

public interface VehicleService {

    VehicleResponse createVehicle(CreateVehicleCommand command);
}
