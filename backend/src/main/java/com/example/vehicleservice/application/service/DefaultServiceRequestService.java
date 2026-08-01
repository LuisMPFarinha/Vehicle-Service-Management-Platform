package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.dto.ServiceRequestResponse;
import com.example.vehicleservice.application.exception.VehicleNotFoundException;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import com.example.vehicleservice.domain.repository.ServiceRequestRepository;
import com.example.vehicleservice.domain.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DefaultServiceRequestService implements ServiceRequestService {
    private final ServiceRequestRepository serviceRequestRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public ServiceRequestResponse openRequest(OpenServiceRequestCommand command) {
        if(vehicleRepository.findById(command.vehicleId()).isEmpty()){
            throw new VehicleNotFoundException(command.vehicleId());
        }

        ServiceRequest serviceRequest = ServiceRequest.create(
            command.vehicleId(),
            command.description(),
            command.priority()
        );

        ServiceRequest savedServiceRequest = serviceRequestRepository.save(serviceRequest);

        return new ServiceRequestResponse(
            savedServiceRequest.getId(),
            savedServiceRequest.getVehicleId(),
            savedServiceRequest.getDescription(),
            savedServiceRequest.getPriority(),
            savedServiceRequest.getStatus(),
            savedServiceRequest.getAssignedTechnician(),
            savedServiceRequest.getCreatedAt(),
            savedServiceRequest.getCompletedAt()
        );
    }

    @Override
    public ServiceRequestResponse assignTechnician(AssignTechnicianCommand command) {


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
