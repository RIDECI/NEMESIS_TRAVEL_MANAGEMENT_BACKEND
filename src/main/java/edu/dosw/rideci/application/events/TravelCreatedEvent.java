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
public class TravelCreatedEvent {

    private Long travelId;

    private Long driverId;

    private Location origin;

    private Location destiny;

    private LocalDateTime departureDateAndTime;

}
