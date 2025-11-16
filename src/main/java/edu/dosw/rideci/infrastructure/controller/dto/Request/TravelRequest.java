package edu.dosw.rideci.infrastructure.controller.dto.Request;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelRequest {

    private String id;

    private String driverId;

    private int availableSlots;

    private double estimatedCost;

    private LocalDateTime departureDateAndTime;

    private List<String> passengersId;

    private String conditions;

    private OriginRequest origin;

    private DestinyRequest destiny;
}
