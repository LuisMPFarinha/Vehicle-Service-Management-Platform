package com.example.vehicleservice.presentation.error;

import com.example.vehicleservice.application.exception.*;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    ApiErrorResponse handleIllegalArgument(IllegalArgumentException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(VehicleAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ApiErrorResponse handleVehicleAlreadyExists(VehicleAlreadyExistsException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiErrorResponse handleVehicleNotFound(VehicleNotFoundException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ServiceRequestAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ApiErrorResponse handleServiceRequestAlreadyExists(ServiceRequestAlreadyExistsException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(AssignTechnicianOnInactiveServiceRequestException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    ApiErrorResponse handleAssignTechnicianOnInactiveServiceRequest(AssignTechnicianOnInactiveServiceRequestException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }

    @ExceptionHandler(ServiceRequestNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    ApiErrorResponse handleServiceRequestNotFound(ServiceRequestNotFoundException exception) {
        return new ApiErrorResponse(exception.getMessage());
    }
}
