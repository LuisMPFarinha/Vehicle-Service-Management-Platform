package com.example.vehicleservice.infrastructure.configuration;

import com.example.vehicleservice.application.service.ServiceCompletedPublisher;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PlaceholderServiceCompletedPublisher implements ServiceCompletedPublisher {

    @Override
    public void publishServiceCompleted(UUID serviceRequestId) {
        // TODO: Replace with an SNS/SQS adapter in the AWS exercise.
    }
}
