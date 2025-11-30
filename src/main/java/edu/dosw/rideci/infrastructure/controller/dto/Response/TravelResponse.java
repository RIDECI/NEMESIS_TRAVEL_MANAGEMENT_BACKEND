package edu.dosw.rideci.infrastructure.controller.dto.Response;

import java.time.LocalDateTime;
import java.util.List;

import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.domain.model.enums.TravelType;
import edu.dosw.rideci.infrastructure.controller.dto.Request.LocationRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TravelResponse {

    private String id;

    private String organizerId;

    private String driverId;

    private int availableSlots;

    private Status status;

    private TravelType travelType;

    private double estimatedCost;

    private LocalDateTime departureDateAndTime;

    private List<String> passengersId;

    private String conditions;

    private LocationRequest origin;

    private LocationRequest destiny;

}
