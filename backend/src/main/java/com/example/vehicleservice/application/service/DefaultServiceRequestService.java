package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.dto.ServiceRequestResponse;
import com.example.vehicleservice.application.exception.ServiceRequestAlreadyExistsException;
import com.example.vehicleservice.application.exception.ServiceRequestNotFoundException;
import com.example.vehicleservice.application.exception.VehicleNotFoundException;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.repository.ServiceRequestRepository;
import com.example.vehicleservice.domain.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

        if(serviceRequestRepository.existsActiveDuplicate(command.vehicleId(), command.description())) {
            throw new ServiceRequestAlreadyExistsException(command.vehicleId(), command.description());
        }

        ServiceRequest serviceRequest = ServiceRequest.create(
            command.vehicleId(),
            command.description(),
            command.priority()
        );

        ServiceRequest savedServiceRequest = serviceRequestRepository.save(serviceRequest);

        return toServiceRequestResponse(savedServiceRequest);
    }

    @Override
    public ServiceRequestResponse assignTechnician(AssignTechnicianCommand command) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(command.serviceRequestId())
            .orElseThrow(() -> new ServiceRequestNotFoundException(command.serviceRequestId()));

        serviceRequest.assignTechnician(command.technicianName());

        ServiceRequest savedServiceRequest = serviceRequestRepository.save(serviceRequest);

        return toServiceRequestResponse(savedServiceRequest);
    }

    @Override
    public ServiceRequestResponse completeRequest(UUID id) {
        ServiceRequest serviceRequest = serviceRequestRepository.findById(id)
            .orElseThrow(() -> new ServiceRequestNotFoundException(id));

        serviceRequest.complete();

        ServiceRequest savedServiceRequest = serviceRequestRepository.save(serviceRequest);

        return toServiceRequestResponse(savedServiceRequest);
    }

    private ServiceRequestResponse toServiceRequestResponse(ServiceRequest serviceRequest) {
        return new ServiceRequestResponse(
            serviceRequest.getId(),
            serviceRequest.getVehicleId(),
            serviceRequest.getDescription(),
            serviceRequest.getPriority(),
            serviceRequest.getStatus(),
            serviceRequest.getAssignedTechnician(),
            serviceRequest.getCreatedAt(),
            serviceRequest.getCompletedAt()
        );
    }
}
