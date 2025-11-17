package edu.dosw.rideci.application.port.in;

import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.Enum.Status;

public interface ChangeStateTravelUseCase {

    Travel changeStateTravel(Long id, Status state);

}