package com.example.vehicleservice.unit;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.Test;

import com.example.vehicleservice.domain.model.Vehicle;

class VehicleRulesTest {

    @Test
    void registrationNumberIsRequired() {
        assertThatThrownBy(() -> Vehicle.create(" ", "Volkswagen Golf", "Luis"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Registration number is required");
    }

    @Test
    void modelIsRequired() {
        assertThatThrownBy(() -> Vehicle.create("AA-00-BB", " ", "Luis"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Model is required");
    }

    @Test
    void ownerNameIsRequired() {
        assertThatThrownBy(() -> Vehicle.create("AA-00-BB", "Volkswagen Golf", " "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Owner name is required");
    }
}
