package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.dto.ServiceRequestResponse;
import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ServiceRequestService {
    ServiceRequestResponse openRequest(OpenServiceRequestCommand command);
    ServiceRequestResponse assignTechnician(AssignTechnicianCommand command);
    ServiceRequestResponse completeRequest(UUID id);
    Page<ServiceRequestResponse> findFiltered(ServiceRequestStatus status, Priority priority, String regNum, Pageable pageable);
}
