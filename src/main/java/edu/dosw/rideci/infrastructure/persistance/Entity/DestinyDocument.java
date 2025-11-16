package edu.dosw.rideci.infrastructure.persistance.Entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestinyDocument {

    private double latitude;

    private double longitude;

    private String direction;

}