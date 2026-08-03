package com.example.vehicleservice.integration;

import static org.assertj.core.api.Assertions.assertThat;
import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.Vehicle;
import com.example.vehicleservice.domain.repository.ServiceRequestRepository;
import com.example.vehicleservice.domain.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class ServiceRequestRepositoryIntegrationTest {
    @Autowired
    ServiceRequestRepository serviceRequestRepository;
    @Autowired
    VehicleRepository vehicleRepository;

    private Vehicle savedVehicle;
    private ServiceRequest savedServiceRequest;

    @BeforeEach
    void setUp() {
        savedVehicle = vehicleRepository.save(Vehicle.create(
            "SR-REPO-00-01",
            "Tesla",
            "Mudambi"
        ));
        savedServiceRequest = serviceRequestRepository.save(ServiceRequest.create(
            savedVehicle.getId(),
            "tire change",
            Priority.MEDIUM
        ));
    }

    @Test
    void returnsTrueWhenActiveDuplicateExists() {
        boolean result = serviceRequestRepository.existsActiveDuplicate(savedVehicle.getId(), "tire change");
        assertThat(result).isTrue();
    }

    @Test
    void returnsFalseWhenSameVehicleHasDifferentDescription() {
        boolean result = serviceRequestRepository.existsActiveDuplicate(savedVehicle.getId(), "change filter");
        assertThat(result).isFalse();
    }

    @Test
    void savesAssignedTechnician() {
        savedServiceRequest.assignTechnician("Tiago");

        ServiceRequest result = serviceRequestRepository.save(savedServiceRequest);

        assertThat(result.getAssignedTechnician()).isEqualTo("Tiago");
    }
}
