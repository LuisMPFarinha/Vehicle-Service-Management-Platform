package com.example.vehicleservice.presentation.controller;

import com.example.vehicleservice.application.dto.CreateVehicleCommand;
import com.example.vehicleservice.application.dto.VehicleResponse;
import com.example.vehicleservice.application.service.VehicleApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleApplicationService vehicleApplicationService;

    public VehicleController(VehicleApplicationService vehicleApplicationService) {
        this.vehicleApplicationService = vehicleApplicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    VehicleResponse createVehicle(@RequestBody CreateVehicleCommand command) {
        // TODO: Add request validation and proper error handling in the exercise.
        return vehicleApplicationService.createVehicle(command);
    }
}
