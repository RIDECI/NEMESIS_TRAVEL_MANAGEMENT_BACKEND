package edu.dosw.rideci.application.port.out;

import edu.dosw.rideci.domain.model.Travel;

public interface TravelRepositoryPort {

    Travel save(Travel travel);

}
