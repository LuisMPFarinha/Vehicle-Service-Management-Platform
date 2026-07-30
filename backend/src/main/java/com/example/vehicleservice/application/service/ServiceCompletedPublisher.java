package com.example.vehicleservice.application.service;

import java.util.UUID;

public interface ServiceCompletedPublisher {

    void publishServiceCompleted(UUID serviceRequestId);
}
