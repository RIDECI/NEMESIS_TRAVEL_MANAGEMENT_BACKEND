package edu.dosw.rideci.application.port.in;

import java.util.List;

public interface GetPassengerListUseCase {

    List<Long> getPassengerList(Long id, List<Long> passengersId);

}
