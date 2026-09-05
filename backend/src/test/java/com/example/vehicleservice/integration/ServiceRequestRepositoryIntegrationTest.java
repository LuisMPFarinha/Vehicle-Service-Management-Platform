package com.example.vehicleservice.integration;

import static org.assertj.core.api.Assertions.assertThat;
import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import com.example.vehicleservice.domain.model.Vehicle;
import com.example.vehicleservice.domain.repository.ServiceRequestRepository;
import com.example.vehicleservice.domain.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@Sql(statements = {"DELETE FROM service_requests", "DELETE FROM vehicles"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ServiceRequestRepositoryIntegrationTest {
    @Autowired
    ServiceRequestRepository srRepository;
    @Autowired
    VehicleRepository vRepository;

    private Vehicle savedVehicle;
    private ServiceRequest savedServiceRequest;

    private List<Vehicle> vList;
    private List<ServiceRequest> srList;

    @BeforeEach
    void setUp() {
        vList = new ArrayList<>();
        srList = new ArrayList<>();

        savedVehicle = vRepository.save(Vehicle.create(
            "SR-REPO-00-01",
            "Tesla",
            "Mudambi"
        ));
        savedServiceRequest = srRepository.save(ServiceRequest.create(
            savedVehicle.getId(),
            "tire change",
            Priority.MEDIUM
        ));

        vList.add(vRepository.save(Vehicle.create("01", "Kia", "Rui")));
        vList.add(vRepository.save(Vehicle.create("02", "Seat", "Ana")));
        vList.add(vRepository.save(Vehicle.create("03", "Audi", "Mel")));

        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.getFirst().getId(), "case 0", Priority.LOW, ServiceRequestStatus.OPEN, "Tiago", Instant.now(), null)));
        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.getFirst().getId(), "case 1", Priority.MEDIUM, ServiceRequestStatus.COMPLETED, "Tiago", Instant.now(), Instant.now())));
        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.getFirst().getId(), "case 2", Priority.HIGH, ServiceRequestStatus.IN_PROGRESS, "Tiago", Instant.now(), null)));
        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.get(1).getId(), "case 3", Priority.URGENT, ServiceRequestStatus.CANCELLED, "Tiago", Instant.now(), null)));
        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.get(1).getId(), "case 4", Priority.MEDIUM, ServiceRequestStatus.WAITING_FOR_PARTS, "Tiago", Instant.now(), null)));
        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.get(1).getId(), "case 5", Priority.HIGH, ServiceRequestStatus.OPEN, "Tiago", Instant.now(), null)));
        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.get(2).getId(), "case 6", Priority.MEDIUM, ServiceRequestStatus.WAITING_FOR_PARTS, "Tiago", Instant.now(), null)));
        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.get(2).getId(), "case 7", Priority.MEDIUM, ServiceRequestStatus.CANCELLED, "Tiago", Instant.now(), null)));
        srList.add(srRepository.save(ServiceRequest.restore(UUID.randomUUID(), vList.get(2).getId(), "case 8", Priority.HIGH, ServiceRequestStatus.OPEN, "Tiago", Instant.now(), null)));
    }

    @Test
    void returnsTrueWhenActiveDuplicateExists() {
        boolean result = srRepository.existsActiveDuplicate(savedVehicle.getId(), "tire change");
        assertThat(result).isTrue();
    }

    @Test
    void returnsFalseWhenSameVehicleHasDifferentDescription() {
        boolean result = srRepository.existsActiveDuplicate(savedVehicle.getId(), "change filter");
        assertThat(result).isFalse();
    }

    @Test
    void savesAssignedTechnician() {
        savedServiceRequest.assignTechnician("Tiago");

        ServiceRequest result = srRepository.save(savedServiceRequest);

        assertThat(result.getAssignedTechnician()).isEqualTo("Tiago");
    }

    @Test
    void completeAServiceRequest() {
        savedServiceRequest.assignTechnician("Tiago");
        ServiceRequest requestWithTechnician = srRepository.save(savedServiceRequest);
        requestWithTechnician.complete();
        ServiceRequest result = srRepository.save(requestWithTechnician);
        assertThat(result.getStatus()).isEqualTo(ServiceRequestStatus.COMPLETED);
        assertThat(result.getCompletedAt()).isNotNull();
    }

    @Test
    void getRequestsFilteredByStatus() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ServiceRequest> result = srRepository.findFiltered(ServiceRequestStatus.OPEN, null, null, pageable);

        assertThat(result.getNumberOfElements()).isEqualTo(4);
        assertThat(result).extracting(ServiceRequest::getId)
            .contains(savedServiceRequest.getId(), srList.get(0).getId(), srList.get(5).getId(), srList.get(8).getId());
    }

    @Test
    void getRequestsFilteredByStatusAndPriority() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ServiceRequest> result = srRepository.findFiltered(ServiceRequestStatus.WAITING_FOR_PARTS, Priority.MEDIUM, null, pageable);

        assertThat(result.getNumberOfElements()).isEqualTo(2);
        assertThat(result).extracting(ServiceRequest::getId)
            .contains(srList.get(4).getId(), srList.get(6).getId());
    }

    @Test
    void getRequestsFilteredByStatusAndPriorityAndVehicleRegistration() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ServiceRequest> result = srRepository.findFiltered(ServiceRequestStatus.COMPLETED, Priority.MEDIUM, "01", pageable);

        assertThat(result.getNumberOfElements()).isEqualTo(1);
        assertThat(result).extracting(ServiceRequest::getId).contains(srList.get(1).getId());
    }
}
