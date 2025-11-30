package edu.dosw.rideci.domain.model;

import java.time.LocalDateTime;
import java.util.List;

import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.domain.model.enums.TravelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Travel {

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

    private Location origin;

    private Location destiny;

}