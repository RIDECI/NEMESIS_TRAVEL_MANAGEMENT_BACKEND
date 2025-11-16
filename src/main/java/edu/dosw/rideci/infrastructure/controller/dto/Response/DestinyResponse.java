package edu.dosw.rideci.infrastructure.controller.dto.Response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DestinyResponse {

    private double longitude;

    private double latitude;

    private String direction;

}
