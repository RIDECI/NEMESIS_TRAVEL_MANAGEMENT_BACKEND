package edu.dosw.rideci.infrastructure.persistance.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LocationDocument {

    private double latitude;

    private double longitude;

    private String direction;

}
