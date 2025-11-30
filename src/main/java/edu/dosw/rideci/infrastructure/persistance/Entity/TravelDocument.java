package edu.dosw.rideci.infrastructure.persistance.Entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.domain.model.enums.TravelType;
import lombok.Builder;
import lombok.Data;

@Document(collection = "travel")
@Data
@Builder
public class TravelDocument {

    @Id
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

    private LocationDocument origin;

    private LocationDocument destiny;

}
