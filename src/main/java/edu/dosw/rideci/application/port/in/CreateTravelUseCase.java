package edu.dosw.rideci.application.port.in;

import edu.dosw.rideci.domain.model.Travel;

public interface CreateTravelUseCase {

    Travel createTravel(Travel travel);

}
