package com.example.vehicleservice.infrastructure.configuration;

import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequest;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import com.example.vehicleservice.domain.model.Vehicle;
import com.example.vehicleservice.domain.repository.ServiceRequestRepository;
import com.example.vehicleservice.domain.repository.VehicleRepository;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Configuration
@Profile("dev")
public class DevDataSeeder {

    @Bean
    ApplicationRunner seedDevData(VehicleRepository vehicleRepository, ServiceRequestRepository serviceRequestRepository) {
        return args -> {
            if (vehicleRepository.findByRegistrationNumber("DEV-00-01").isPresent()) {
                return;
            }

            Vehicle golf = vehicleRepository.save(Vehicle.create("DEV-00-01", "Volkswagen Golf", "Luis Martins"));
            Vehicle id4 = vehicleRepository.save(Vehicle.create("DEV-00-02", "Volkswagen ID.4", "Ana Silva"));
            Vehicle passat = vehicleRepository.save(Vehicle.create("DEV-00-03", "Volkswagen Passat", "Rui Costa"));

            Instant now = Instant.now();

            serviceRequestRepository.save(ServiceRequest.restore(
                UUID.randomUUID(),
                golf.getId(),
                "Replace front brake pads",
                Priority.HIGH,
                ServiceRequestStatus.OPEN,
                null,
                now.minus(3, ChronoUnit.DAYS),
                null
            ));

            serviceRequestRepository.save(ServiceRequest.restore(
                UUID.randomUUID(),
                id4.getId(),
                "Diagnose intermittent charging warning",
                Priority.URGENT,
                ServiceRequestStatus.IN_PROGRESS,
                "Tiago",
                now.minus(2, ChronoUnit.DAYS),
                null
            ));

            serviceRequestRepository.save(ServiceRequest.restore(
                UUID.randomUUID(),
                passat.getId(),
                "Annual inspection and oil service",
                Priority.MEDIUM,
                ServiceRequestStatus.WAITING_FOR_PARTS,
                "Mariana",
                now.minus(1, ChronoUnit.DAYS),
                null
            ));

            serviceRequestRepository.save(ServiceRequest.restore(
                UUID.randomUUID(),
                golf.getId(),
                "Replace cabin air filter",
                Priority.LOW,
                ServiceRequestStatus.COMPLETED,
                "Sofia",
                now.minus(8, ChronoUnit.DAYS),
                now.minus(6, ChronoUnit.DAYS)
            ));
        };
    }
}
