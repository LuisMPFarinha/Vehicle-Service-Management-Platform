package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.dto.ServiceRequestResponse;

import java.util.UUID;

public interface ServiceRequestService {

    ServiceRequestResponse openRequest(OpenServiceRequestCommand command);

    ServiceRequestResponse assignTechnician(AssignTechnicianCommand command);

    ServiceRequestResponse completeRequest(UUID id);
}
