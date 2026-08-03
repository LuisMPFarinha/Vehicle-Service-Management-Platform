package com.example.vehicleservice.acceptance;

import com.example.vehicleservice.application.dto.*;
import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import com.example.vehicleservice.presentation.error.ApiErrorResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.shaded.com.google.common.util.concurrent.Service;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class OpenServiceRequestAcceptanceTest {
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void opensServiceRequestForExistingVehicle() {
        var vehicleResponse = createVehicle("AA-00-01");
        assertThat(vehicleResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var serviceRequestResponse = openServiceRequest(
            vehicleResponse.getBody().id(),
            "Needs to change tires",
            Priority.MEDIUM
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
        var vehicleResponse = createVehicle("AA-00-02");
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

    @Test
    void AssignsTechnicianToActiveServiceRequest() {
        var vehicleResponse = createVehicle("AA-00-03");
        assertThat(vehicleResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        var openServiceResponse = openServiceRequest(
            vehicleResponse.getBody().id(),
            "Needs to change tires",
            Priority.MEDIUM
        );
        assertThat(openServiceResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        AssignTechnicianCommand assignTechnicianCommand = new AssignTechnicianCommand(
            openServiceResponse.getBody().id(),
            "Tiago"
        );

        var assignTechnicianResponse = restTemplate.patchForObject(
            "/api/service-requests/" + openServiceResponse.getBody().id() + "/technician",
            assignTechnicianCommand,
            ServiceRequestResponse.class
        );

        assertThat(assignTechnicianResponse.assignedTechnician()).isEqualTo("Tiago");
        assertThat(assignTechnicianResponse.status()).isEqualTo(ServiceRequestStatus.OPEN);
    }

    private ResponseEntity<VehicleResponse> createVehicle(String registrationNumber) {
        CreateVehicleCommand command = new CreateVehicleCommand(
            registrationNumber,
            "Volkswagen Golf",
            "Luis"
        );
        return restTemplate.postForEntity(
            "/api/vehicles",
            command,
            VehicleResponse.class
        );
    }

    private ResponseEntity<ServiceRequestResponse> openServiceRequest(UUID vehicleId, String description, Priority priority) {
        OpenServiceRequestCommand serviceRequestCommand = new OpenServiceRequestCommand(
            vehicleId,
            description,
            priority
        );

        return restTemplate.postForEntity(
            "/api/service-requests",
            serviceRequestCommand,
            ServiceRequestResponse.class
        );
    }
}
