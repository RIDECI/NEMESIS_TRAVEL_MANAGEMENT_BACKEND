package edu.dosw.rideci.application.service;

import org.springframework.stereotype.Service;

import edu.dosw.rideci.application.port.in.CreateTravelUseCase;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.application.port.out.TravelRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelService implements CreateTravelUseCase {

    private final TravelRepositoryPort travelRepositoryPort;

    @Override
    public Travel createTravel(Travel travel) {
        return travelRepositoryPort.save(travel);
    }

}
