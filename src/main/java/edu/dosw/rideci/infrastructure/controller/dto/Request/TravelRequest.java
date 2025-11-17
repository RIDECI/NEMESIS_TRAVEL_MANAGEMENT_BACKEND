package edu.dosw.rideci.infrastructure.controller.dto.Request;

import java.time.LocalDateTime;
import java.util.List;

import edu.dosw.rideci.domain.model.enums.Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelRequest {

    private Long id;

    private Long driverId;

    private int availableSlots;

    private Status status;

    private double estimatedCost;

    private LocalDateTime departureDateAndTime;

    private List<Long> passengersId;

    private String conditions;

    private LocationRequest origin;

    private LocationRequest destiny;

}
