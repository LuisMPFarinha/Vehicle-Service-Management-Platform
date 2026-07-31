package com.example.vehicleservice.acceptance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.example.vehicleservice.application.dto.CreateVehicleCommand;
import com.example.vehicleservice.application.dto.VehicleResponse;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateVehicleAcceptanceTest {
    
    @Autowired
    TestRestTemplate restTemplate;

    @Test
    void createVehicle() {
        CreateVehicleCommand command = new CreateVehicleCommand(
                "API-00-01",
                "Volkswagen Golf",
                "Luis");

        var response = restTemplate.postForEntity(
            "/api/vehicles", 
            command, 
            VehicleResponse.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().registrationNumber()).isEqualTo("API-00-01");
        assertThat(response.getBody().model()).isEqualTo("Volkswagen Golf");
        assertThat(response.getBody().ownerName()).isEqualTo("Luis");
        assertThat(response.getBody().id()).isNotNull();
    }

    @Test
    void rejectsVehicleWithoutRegistrationNumber() {
        CreateVehicleCommand command = new CreateVehicleCommand(
                " ",
                "Volkswagen Golf",
                "Luis");

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/api/vehicles",
            command,
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    }

    @Test
    void rejectsDuplicateRegistrationNumber() {
        CreateVehicleCommand firstVehicle = new CreateVehicleCommand(
                "API-00-02",
                "Volkswagen Golf",
                "Luis");
        CreateVehicleCommand duplicateVehicle = new CreateVehicleCommand(
                "API-00-02",
                "Volkswagen Polo",
                "Maria");

        restTemplate.postForEntity("/api/vehicles", firstVehicle, VehicleResponse.class);

        ResponseEntity<String> response = restTemplate.postForEntity(
            "/api/vehicles",
            duplicateVehicle,
            String.class
        );

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CONFLICT);
    }
}
