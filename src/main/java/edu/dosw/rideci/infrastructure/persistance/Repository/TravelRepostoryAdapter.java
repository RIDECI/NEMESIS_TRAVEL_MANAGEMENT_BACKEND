package edu.dosw.rideci.infrastructure.persistance.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.dosw.rideci.application.port.out.TravelRepositoryPort;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.Enum.Status;
import edu.dosw.rideci.exceptions.TravelNotFoundException;
import edu.dosw.rideci.infrastructure.persistance.Entity.TravelDocument;
import edu.dosw.rideci.infrastructure.persistance.Repository.mapper.TravelMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TravelRepostoryAdapter implements TravelRepositoryPort {

    private final TravelRepository travelRepository;
    private final TravelMapper travelMapper;

    @Override
    public Travel save(Travel travel) {
        TravelDocument document = travelMapper.toDocument(travel);

        TravelDocument savedTravel = travelRepository.save(document);

        return travelMapper.toDomain(savedTravel);
    }

    @Override
    public Travel getTravelById(Long id) {

        TravelDocument travel = travelRepository.findById(id)
                .orElseThrow(() -> new TravelNotFoundException("The travel with id: {id}, doesnt exists "));

        return travelMapper.toDomain(travel);

    }

    @Override
    public void deleteTravelById(Long id) {

        travelRepository.deleteById(id);

    }

    @Override
    public Travel updateTravel(Long id, Travel travel) {

        TravelDocument actualTravel = travelRepository.findById(id)
                .orElseThrow(() -> new TravelNotFoundException("Dont exist the travel with id: {id}"));

        actualTravel.setAvailableSlots(travel.getAvailableSlots());
        actualTravel.setEstimatedCost(travel.getEstimatedCost());
        actualTravel.setDepartureDateAndTime(travel.getDepartureDateAndTime());
        actualTravel.setPassengersId(travel.getPassengersId());
        actualTravel.setConditions(travel.getConditions());
        actualTravel.setOrigin(travelMapper.toOriginEmbeddable(travel.getOrigin()));
        actualTravel.setDestiny(travelMapper.toDestinyEmbeddable(travel.getDestiny()));

        TravelDocument travelUpdated = travelRepository.save(actualTravel);

        return travelMapper.toDomain(travelUpdated);

    }

    @Override
    public List<Travel> getAllTravels() {

        List<TravelDocument> allTravels = travelRepository.findAll();

        return travelMapper.toListDomain(allTravels);

    }

    @Override
    public Travel changeStateTravel(Long id, Status status) {

        TravelDocument travelToModifyState = travelRepository.findById(id).orElseThrow(
                () -> new TravelNotFoundException("The trip to change the state with id: {id} does not exist "));

        travelToModifyState.setStatus(status);

        TravelDocument travelUpdated = travelRepository.save(travelToModifyState);

        return travelMapper.toDomain(travelUpdated);

    }

}
