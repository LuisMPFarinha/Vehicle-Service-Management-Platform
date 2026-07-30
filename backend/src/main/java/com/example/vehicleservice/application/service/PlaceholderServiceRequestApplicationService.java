package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.dto.ServiceRequestResponse;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PlaceholderServiceRequestApplicationService implements ServiceRequestApplicationService {

    @Override
    public ServiceRequestResponse openRequest(OpenServiceRequestCommand command) {
        // TODO: Implement duplicate detection, vehicle lookup, and persistence test-first.
        return new ServiceRequestResponse(
                UUID.randomUUID(),
                command.vehicleId(),
                command.description(),
                command.priority(),
                ServiceRequestStatus.OPEN,
                null,
                Instant.now(),
                null
        );
    }

    @Override
    public ServiceRequestResponse assignTechnician(AssignTechnicianCommand command) {
        // TODO: Implement active-state reassignment rules test-first.
        return new ServiceRequestResponse(
                command.serviceRequestId(),
                null,
                null,
                null,
                ServiceRequestStatus.IN_PROGRESS,
                command.technicianName(),
                null,
                null
        );
    }
}
