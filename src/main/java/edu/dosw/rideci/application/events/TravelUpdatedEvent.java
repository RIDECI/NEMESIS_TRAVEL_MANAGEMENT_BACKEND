package edu.dosw.rideci.application.events;

import java.time.LocalDateTime;

import edu.dosw.rideci.domain.model.Location;
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
public class TravelUpdatedEvent {

    private String travelId;

    private int availableSlots;

    private double estimatedCost;

    private LocalDateTime departureDateAndTime;

    private Location origin;

    private Location destiny;

}
