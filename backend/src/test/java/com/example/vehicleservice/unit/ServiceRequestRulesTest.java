package com.example.vehicleservice.unit;

import com.example.vehicleservice.application.dto.AssignTechnicianCommand;
import com.example.vehicleservice.application.dto.OpenServiceRequestCommand;
import com.example.vehicleservice.application.exception.ServiceRequestAlreadyExistsException;
import com.example.vehicleservice.application.exception.VehicleNotFoundException;
import com.example.vehicleservice.application.service.DefaultServiceRequestService;
import com.example.vehicleservice.domain.exception.DomainRuleViolationException;
import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import com.example.vehicleservice.domain.model.Vehicle;
import com.example.vehicleservice.domain.repository.ServiceRequestRepository;
import com.example.vehicleservice.domain.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServiceRequestRulesTest {
    @Mock
    private ServiceRequestRepository serviceRequestRepository;
    @Mock
    private VehicleRepository vehicleRepository;
    private DefaultServiceRequestService service;

    private Vehicle vehicle;
    private String description;
    private OpenServiceRequestCommand openServiceRequestCommand;

    @BeforeEach
    void setUp() {
        service = new DefaultServiceRequestService(
            serviceRequestRepository,
            vehicleRepository
        );

        vehicle = Vehicle.create("BB-00-01", "Seat", "Marco");
        description = "tire change";
        openServiceRequestCommand = new OpenServiceRequestCommand(
            vehicle.getId(),
            description,
            Priority.MEDIUM
        );
    }

    @Test
    void serviceRequestVehicleIsRequired() {
        UUID vehicleId = UUID.randomUUID();
        when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.openRequest(new OpenServiceRequestCommand(
            vehicleId,
            description,
            Priority.MEDIUM
        ))).isInstanceOf(VehicleNotFoundException.class).hasMessage("VehicleId " + vehicleId + " not found");
    }

    @Test
    void throwsWhenActiveDuplicateExists() {
        when(vehicleRepository.findById(vehicle.getId())).thenReturn(Optional.of(vehicle));
        when(serviceRequestRepository.existsActiveDuplicate(vehicle.getId(), description)).thenReturn(true);

        assertThatThrownBy(() -> service.openRequest(openServiceRequestCommand))
            .isInstanceOf(ServiceRequestAlreadyExistsException.class);

        verify(serviceRequestRepository, never()).save(any());
    }

    @Test
    void throwsWhenTechnicianAssignedToInactiveServiceRequest() {
        ServiceRequest cancelledRequest = ServiceRequest.restore(
            UUID.randomUUID(),
            vehicle.getId(),
            description,
            Priority.MEDIUM,
            ServiceRequestStatus.CANCELLED,
            null,
            Instant.now(),
            null
        );

        assertThatThrownBy(() -> cancelledRequest.assignTechnician("Tiago"))
            .isInstanceOf(DomainRuleViolationException.class);
    }

    @Test
    @Disabled("Training exercise: implement the domain rule test-first.")
    void completedRequestCannotReturnToOpen() {
        // Given a completed service request
        // When a transition to OPEN is attempted
        // Then a domain rule violation should be raised
    }

    @Test
    @Disabled("Training exercise: implement the completion rule test-first.")
    void requestCannotBeCompletedWithoutAssignedTechnician() {
        // Given an open service request without an assigned technician
        // When it is completed
        // Then a domain rule violation should be raised
    }
}
