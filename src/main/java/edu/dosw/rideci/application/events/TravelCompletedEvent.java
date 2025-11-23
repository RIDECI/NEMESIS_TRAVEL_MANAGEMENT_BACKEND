package edu.dosw.rideci.application.events;

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

    private Long travelId;

    private Status state;

}