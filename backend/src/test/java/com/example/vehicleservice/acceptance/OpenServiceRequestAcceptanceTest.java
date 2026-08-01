package com.example.vehicleservice.acceptance;

import com.example.vehicleservice.application.dto.*;
import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import com.example.vehicleservice.presentation.error.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OpenServiceRequestAcceptanceTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void opensServiceRequestForExistingVehicle() {
        var createCommand = createVehicleCommand("AA-00-01");
        var vehicleResponse = restTemplate.postForEntity(
            "/api/vehicles",
            createCommand,
            VehicleResponse.class
        );

        assertThat(vehicleResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        OpenServiceRequestCommand serviceRequestCommand = new OpenServiceRequestCommand(
            vehicleResponse.getBody().id(),
            "Needs to change tires",
            Priority.MEDIUM
        );

        var serviceRequestResponse = restTemplate.postForEntity(
            "/api/service-requests",
            serviceRequestCommand,
            ServiceRequestResponse.class
        );

        assertThat(serviceRequestResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(serviceRequestResponse.getBody()).isNotNull();
        assertThat(serviceRequestResponse.getBody().id()).isNotNull();
        assertThat(serviceRequestResponse.getBody().vehicleId()).isEqualTo(vehicleResponse.getBody().id());
        assertThat(serviceRequestResponse.getBody().description()).isEqualTo("Needs to change tires");
        assertThat(serviceRequestResponse.getBody().priority()).isEqualTo(Priority.MEDIUM);
        assertThat(serviceRequestResponse.getBody().status()).isEqualTo(ServiceRequestStatus.OPEN);
        assertThat(serviceRequestResponse.getBody().assignedTechnician()).isNull();
        assertThat(serviceRequestResponse.getBody().createdAt()).isNotNull();
        assertThat(serviceRequestResponse.getBody().completedAt()).isNull();
    }

    @Test
    void rejectsDuplicateActiveServiceRequest() {
        var createCommand = createVehicleCommand("AA-00-02");
        var vehicleResponse = restTemplate.postForEntity(
            "/api/vehicles",
            createCommand,
            VehicleResponse.class
        );

        assertThat(vehicleResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        OpenServiceRequestCommand serviceRequestCommand = new OpenServiceRequestCommand(
            vehicleResponse.getBody().id(),
            "Needs to change tires",
            Priority.MEDIUM
        );

        restTemplate.postForEntity(
            "/api/service-requests",
            serviceRequestCommand,
            ServiceRequestResponse.class
        );

        var duplicateResponse = restTemplate.postForEntity(
            "/api/service-requests",
            serviceRequestCommand,
            ApiErrorResponse.class
        );

        assertThat(duplicateResponse.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
        assertThat(duplicateResponse.getBody()).isNotNull();
        assertThat(duplicateResponse.getBody().message())
            .isEqualTo("Service request with vehicleId " + vehicleResponse.getBody().id()
                + " and description Needs to change tires already exists");
    }

    private CreateVehicleCommand createVehicleCommand(String registrationNumber) {
        return new CreateVehicleCommand(
            registrationNumber,
            "Volkswagen Golf",
            "Luis"
        );
    }
}
