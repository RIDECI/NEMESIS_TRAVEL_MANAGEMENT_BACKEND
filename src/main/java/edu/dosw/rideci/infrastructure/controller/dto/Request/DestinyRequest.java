package edu.dosw.rideci.infrastructure.controller.dto.Request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestinyRequest {

    private double longitude;

    private double latitude;

    private String direction;

}
