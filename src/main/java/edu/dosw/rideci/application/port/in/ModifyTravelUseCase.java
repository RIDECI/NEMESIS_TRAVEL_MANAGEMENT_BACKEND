package edu.dosw.rideci.application.port.in;

import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;

public interface ModifyTravelUseCase {

    Travel updateTravel(Long id, TravelRequest travel);

}
