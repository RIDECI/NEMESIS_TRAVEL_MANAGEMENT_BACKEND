package edu.dosw.rideci.infrastructure.persistance.Entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import edu.dosw.rideci.domain.model.enums.Status;
import lombok.Builder;
import lombok.Data;

@Document(collection = "travel")
@Data
@Builder
public class TravelDocument {

    @Id
    private Long id;

    private Long driverId;

    private int availableSlots;

    private Status status;

    private double estimatedCost;

    private LocalDateTime departureDateAndTime;

    private List<Long> passengersId;

    private String conditions;

    private LocationDocument origin;

    private LocationDocument destiny;

}
