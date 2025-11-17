package edu.dosw.rideci.infrastructure.controller.dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LocationRequest {

    private double latitude;

    private double longitude;

    private String direction;

}
