package com.example.vehicleservice.presentation.controller;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.dto.ServiceRequestResponse;
import com.example.vehicleservice.application.service.ServiceRequestApplicationService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final ServiceRequestApplicationService serviceRequestApplicationService;

    public ServiceRequestController(ServiceRequestApplicationService serviceRequestApplicationService) {
        this.serviceRequestApplicationService = serviceRequestApplicationService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ServiceRequestResponse openRequest(@RequestBody OpenServiceRequestCommand command) {
        // TODO: Implement behavior test-first.
        return serviceRequestApplicationService.openRequest(command);
    }

    @PatchMapping("/{id}/technician")
    ServiceRequestResponse assignTechnician(@PathVariable UUID id, @RequestBody AssignTechnicianCommand command) {
        // TODO: Decide whether the path id or body id should be the source of truth.
        return serviceRequestApplicationService.assignTechnician(command);
    }
}
