package edu.dosw.rideci.application.events;

import java.time.LocalDateTime;
import java.util.List;

import edu.dosw.rideci.domain.model.Location;
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
public class TravelCreatedEvent {

    private Long travelId;

    private Long driverId;

    private Status state;

    private Location origin;

    private Location destiny;

    private List<Long> passengersId;

    private TravelType travelType;

    private LocalDateTime departureDateAndTime;

}