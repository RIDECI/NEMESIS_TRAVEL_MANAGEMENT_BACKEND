package edu.dosw.rideci.application.port.in;

import java.util.List;

import edu.dosw.rideci.domain.model.Travel;

public interface GetAllTravelsByOrganizerUseCase {

    List<Travel> getAllTravelsByOrganizerId(Long organizerId);

}
