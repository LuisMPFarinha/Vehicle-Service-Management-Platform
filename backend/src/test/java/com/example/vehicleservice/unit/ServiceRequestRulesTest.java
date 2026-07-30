package com.example.vehicleservice.unit;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class ServiceRequestRulesTest {

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
