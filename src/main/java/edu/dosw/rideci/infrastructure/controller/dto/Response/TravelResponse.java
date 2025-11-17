package edu.dosw.rideci.infrastructure.controller.dto.Response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelResponse {

    private Long id;

    private Long driverId;

    private int availableSlots;

    private double estimatedCost;

    private LocalDateTime departureDateAndTime;

    private List<Long> passengersId;

    private String conditions;

    private LocationResponse origin;

    private LocationResponse destiny;

}
