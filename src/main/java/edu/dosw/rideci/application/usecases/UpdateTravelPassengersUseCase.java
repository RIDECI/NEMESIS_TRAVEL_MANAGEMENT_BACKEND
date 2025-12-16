package edu.dosw.rideci.application.usecases;

import java.util.List;

import edu.dosw.rideci.application.port.out.TravelRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateTravelPassengersUseCase {

    private final TravelRepositoryPort travelRepositoryPort;

    public void execute(String travelId, List<Long> passengersIds) {
        // Here we could add logic to validate max passengers, etc.
        // For now, we just delegate to the repository port.
        travelRepositoryPort.updatePassengers(travelId, passengersIds);
    }
}
