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
public class TravelCompletedEvent {

    private String travelId;

    private Long driverId;

    private Long organizerId;

    private TravelType travelType;

    private LocalDateTime departureDateAndTime;

    private List<Long> passengerList;

    private Location destiny;

    private Status state;

}