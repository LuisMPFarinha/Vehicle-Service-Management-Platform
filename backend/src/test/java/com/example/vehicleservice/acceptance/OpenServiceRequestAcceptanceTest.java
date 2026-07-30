package com.example.vehicleservice.acceptance;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Disabled("Training exercise: drive the first service-request endpoint end to end.")
class OpenServiceRequestAcceptanceTest {

    @Test
    void opensServiceRequestForExistingVehicle() {
        // Given an existing vehicle
        // When a user opens a service request
        // Then the API returns a created request with OPEN status
    }
}
