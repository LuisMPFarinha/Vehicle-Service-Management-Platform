package com.example.vehicleservice.presentation.controller;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.dto.ServiceRequestResponse;
import com.example.vehicleservice.application.service.ServiceRequestService;
import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/service-requests")
public class ServiceRequestController {

    private final ServiceRequestService serviceRequestService;

    public ServiceRequestController(ServiceRequestService serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ServiceRequestResponse openRequest(@RequestBody OpenServiceRequestCommand command) {
        return serviceRequestService.openRequest(command);
    }

    @PatchMapping("/{id}/technician")
    ServiceRequestResponse assignTechnician(@PathVariable UUID id, @RequestBody AssignTechnicianCommand command) {
        return serviceRequestService.assignTechnician(new AssignTechnicianCommand(id, command.technicianName()));
    }

    @PatchMapping("/{id}/complete")
    ServiceRequestResponse completeRequest(@PathVariable UUID id) {
        return serviceRequestService.completeRequest(id);
    }

    @GetMapping
    Page<ServiceRequestResponse> findFiltered(
        @RequestParam(required = false) ServiceRequestStatus status,
        @RequestParam(required = false) Priority priority,
        @RequestParam(required = false) String regNum,
        @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)
        Pageable pageable) {
        return serviceRequestService.findFiltered(status, priority, regNum, pageable);
    }
}
