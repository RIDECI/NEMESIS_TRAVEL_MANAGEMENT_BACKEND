package edu.dosw.rideci.application.port.out;

import java.util.List;

import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.Enum.Status;

public interface TravelRepositoryPort {

    Travel save(Travel travel);

    Travel getTravelById(Long id);

    void deleteTravelById(Long id);

    Travel updateTravel(Long id, Travel travel);

    List<Travel> getAllTravels();

    Travel changeStateTravel(Long id, Status status);

}
