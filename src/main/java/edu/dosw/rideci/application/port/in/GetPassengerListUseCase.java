package edu.dosw.rideci.application.port.in;

import java.util.List;

public interface GetPassengerListUseCase {

    List<String> getPassengerList(String id, List<String> passengersId);

}
