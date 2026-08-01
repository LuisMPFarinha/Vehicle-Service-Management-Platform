package com.example.vehicleservice.domain.model;

import java.util.UUID;

class ValidationUtils {
    static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
    static boolean isBlank(UUID value) {
        return value == null;
    }
    static boolean isBlank(Priority value) {
        return value == null;
    }
    static boolean isBlank(ServiceRequestStatus value) {
        return value == null;
    }
}
