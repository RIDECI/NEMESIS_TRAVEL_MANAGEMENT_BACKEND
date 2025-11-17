package edu.dosw.rideci.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import edu.dosw.rideci.application.mapper.TravelMapperInitial;
import edu.dosw.rideci.application.port.in.ChangeStateTravelUseCase;
import edu.dosw.rideci.application.port.in.CreateTravelUseCase;
import edu.dosw.rideci.application.port.in.DeleteTravelUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelUseCase;
import edu.dosw.rideci.application.port.in.GetTravelUseCase;
import edu.dosw.rideci.application.port.in.ModifyTravelUseCase;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.Enum.Status;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;
import edu.dosw.rideci.application.port.out.TravelRepositoryPort;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TravelService implements CreateTravelUseCase, GetTravelUseCase, DeleteTravelUseCase, ModifyTravelUseCase,
        GetAllTravelUseCase, ChangeStateTravelUseCase {

    private final TravelRepositoryPort travelRepositoryPort;
    private final TravelMapperInitial travelMapper;

    @Override
    public Travel createTravel(Travel travel) {
        return travelRepositoryPort.save(travel);
    }

    @Override
    public Travel getTravelById(Long id) {
        return travelRepositoryPort.getTravelById(id);
    }

    @Override
    public void deleteTravelById(Long id) {
        travelRepositoryPort.deleteTravelById(id);
    }

    @Override
    public Travel updateTravel(Long id, TravelRequest travel) {
        Travel travelToModify = travelMapper.toDomain(travel);

        return travelRepositoryPort.updateTravel(id, travelToModify);
    }

    @Override
    public List<Travel> getAllTravels() {

        return travelRepositoryPort.getAllTravels();

    }

    @Override
    public Travel changeStateTravel(Long id, Status status) {

        return travelRepositoryPort.changeStateTravel(id, status);

    }

}
