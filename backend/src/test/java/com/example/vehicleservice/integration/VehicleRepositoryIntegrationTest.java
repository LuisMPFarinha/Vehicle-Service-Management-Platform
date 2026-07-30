package com.example.vehicleservice.integration;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@Disabled("Training exercise: wire the repository adapter to Spring Data JPA.")
class VehicleRepositoryIntegrationTest {

    @Test
    void savesAndFindsVehicleByRegistrationNumber() {
        // Given a valid vehicle
        // When it is saved
        // Then it can be found by registration number
    }
}
