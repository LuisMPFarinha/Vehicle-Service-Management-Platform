package com.example.vehicleservice.application.exception;

import java.util.UUID;

public class AssignTechnicianOnInactiveServiceRequestException extends RuntimeException {
    public AssignTechnicianOnInactiveServiceRequestException(UUID serviceRequestId, String technician) {
        super("Cannot assign technician" + technician + " on inactive service request with id " + serviceRequestId);
    }
}
