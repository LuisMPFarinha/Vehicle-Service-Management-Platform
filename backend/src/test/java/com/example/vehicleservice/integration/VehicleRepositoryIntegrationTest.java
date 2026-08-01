package com.example.vehicleservice.integration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.persistence.EntityManager;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.example.vehicleservice.domain.model.Vehicle;
import com.example.vehicleservice.domain.repository.VehicleRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class VehicleRepositoryIntegrationTest {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    EntityManager entityManager;

    @Test
    void savesAndFindsVehicleByRegistrationNumber() {
        Vehicle savedVehicle = vehicleRepository.save(
            Vehicle.create("REPO-00-01", "Toyota Corolla", "Maria"));

        var foundVehicle = vehicleRepository.findByRegistrationNumber("REPO-00-01");

        assertThat(foundVehicle).isPresent();
        assertThat(foundVehicle.get().getId()).isEqualTo(savedVehicle.getId());
        assertThat(foundVehicle.get().getRegistrationNumber()).isEqualTo("REPO-00-01");
        assertThat(foundVehicle.get().getModel()).isEqualTo("Toyota Corolla");
        assertThat(foundVehicle.get().getOwnerName()).isEqualTo("Maria");
    }

    @Test
    void registrationNumberMustBeUnique() {
        vehicleRepository.save(Vehicle.create("REPO-00-02", "Renault Clio", "Ana"));

        assertThatThrownBy(() -> {
            vehicleRepository.save(Vehicle.create("REPO-00-02", "Peugeot 208", "Joao"));
            entityManager.flush();
        }).isInstanceOf(ConstraintViolationException.class);
    }
}
