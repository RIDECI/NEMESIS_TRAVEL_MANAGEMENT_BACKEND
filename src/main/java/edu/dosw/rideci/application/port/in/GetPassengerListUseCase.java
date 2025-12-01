package edu.dosw.rideci.application.port.in;

import java.util.List;

public interface GetPassengerListUseCase {

    List<Long> getPassengerList(String id, List<Long> passengersId);

}
