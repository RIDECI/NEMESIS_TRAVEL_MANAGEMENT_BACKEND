package edu.dosw.rideci.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.dosw.rideci.application.mapper.TravelMapperInitial;
import edu.dosw.rideci.application.port.in.ChangeStateTravelUseCase;
import edu.dosw.rideci.application.port.in.CreateTravelUseCase;
import edu.dosw.rideci.application.port.in.DeleteTravelUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelByDriverIdUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelsByOrganizerUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelsByPassengerIdUseCase;
import edu.dosw.rideci.application.port.in.GetPassengerListUseCase;
import edu.dosw.rideci.application.port.in.GetTravelUseCase;
import edu.dosw.rideci.application.port.in.ModifyTravelUseCase;
import edu.dosw.rideci.application.port.out.TravelRepositoryPort;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelService implements CreateTravelUseCase, GetTravelUseCase, DeleteTravelUseCase, ModifyTravelUseCase,
        GetAllTravelUseCase, ChangeStateTravelUseCase, GetPassengerListUseCase, GetAllTravelByDriverIdUseCase,
        GetAllTravelsByOrganizerUseCase,
        GetAllTravelsByPassengerIdUseCase {

    private final TravelRepositoryPort travelRepositoryPort;
    private final TravelMapperInitial travelMapper;

    @Override
    public Travel createTravel(TravelRequest travel) {

        Travel travelToCreate = travelMapper.toDomain(travel);

        return travelRepositoryPort.save(travelToCreate);
    }

    @Override
    public Travel getTravelById(String id) {
        return travelRepositoryPort.getTravelById(id);
    }

    @Override
    public void deleteTravelById(String id) {
        travelRepositoryPort.deleteTravelById(id);
    }

    @Override
    public Travel updateTravel(String id, TravelRequest travel) {
        Travel travelToModify = travelMapper.toDomain(travel);

        return travelRepositoryPort.updateTravel(id, travelToModify);
    }

    @Override
    public List<Travel> getAllTravels() {

        return travelRepositoryPort.getAllTravels();

    }

    @Override
    public Travel changeStateTravel(String id, Status status) {

        return travelRepositoryPort.changeStateTravel(id, status);

    }

    @Override
    public List<Long> getPassengerList(String id, List<Long> passengersId) {

        return travelRepositoryPort.getPassengerList(id, passengersId);

    }

    @Override
    public List<Travel> getAllTravelsByDriverId(Long driverId) {

        return travelRepositoryPort.getAllTravelsByDriverId(driverId);

    }

    @Override
    public List<Travel> getAllTravelsByPassengerId(Long passengerId) {

        return travelRepositoryPort.getAllTravelsByPassengerId(passengerId);

    }

    @Override
    public List<Travel> getAllTravelsByOrganizerId(Long organizerId) {
        return travelRepositoryPort.getAllTravelsByOrganizerId(organizerId);
    }

}
