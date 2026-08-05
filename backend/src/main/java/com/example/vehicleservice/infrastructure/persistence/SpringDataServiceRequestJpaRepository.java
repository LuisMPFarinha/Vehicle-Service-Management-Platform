package com.example.vehicleservice.infrastructure.persistence;

import com.example.vehicleservice.domain.model.Priority;
import com.example.vehicleservice.domain.model.ServiceRequestStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.UUID;

public interface SpringDataServiceRequestJpaRepository extends JpaRepository<ServiceRequestEntity, UUID> {
    boolean existsByVehicleIdAndDescriptionAndStatusIn(UUID vehicleId, String description, Collection<ServiceRequestStatus> statuses);

    @Query("""
        SELECT sr
        FROM ServiceRequestEntity sr
        JOIN VehicleEntity v ON sr.vehicleId = v.id
        WHERE (:status IS NULL OR sr.status = :status)
          AND (:priority IS NULL OR sr.priority = :priority)
          AND (:regNum IS NULL OR LOWER(v.registrationNumber) LIKE LOWER(CONCAT('%', :regNum, '%')))
        """)
    Page<ServiceRequestEntity> findFiltered(
        @Param("status") ServiceRequestStatus status,
        @Param("priority") Priority priority,
        @Param("regNum") String regNum,
        Pageable pageable);
}
