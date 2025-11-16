package edu.dosw.rideci.domain.model;

import java.time.LocalDateTime;
import java.util.List;

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

    private Long id;

    private Long driverId;

    private int availableSlots;

    private double estimatedCost;

    private LocalDateTime departureDateAndTime;

    private List<String> passengersId;

    private String conditions;

    private Origin origin;

    private Destiny destiny;

}