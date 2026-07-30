package com.example.vehicleservice.application.service;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.dto.ServiceRequestResponse;

public interface ServiceRequestApplicationService {

    ServiceRequestResponse openRequest(OpenServiceRequestCommand command);

    ServiceRequestResponse assignTechnician(AssignTechnicianCommand command);
}
