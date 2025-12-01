package edu.dosw.rideci.application.port.out;

import java.util.List;

import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.enums.Status;

public interface TravelRepositoryPort {

    Travel save(Travel travel);

    Travel getTravelById(String id);

    void deleteTravelById(String id);

    Travel updateTravel(String id, Travel travel);

    List<Travel> getAllTravels();

    Travel changeStateTravel(String id, Status status);

    List<Long> getPassengerList(String id, List<Long> passengersId);

    List<Travel> getAllTravelsByDriverId(Long driverId);

}