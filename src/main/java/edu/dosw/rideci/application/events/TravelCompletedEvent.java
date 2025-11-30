package edu.dosw.rideci.application.events;

import java.util.List;

import edu.dosw.rideci.domain.model.enums.Status;
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

    private String driverId;

    private List<String> passengerList;

    private Status state;

}