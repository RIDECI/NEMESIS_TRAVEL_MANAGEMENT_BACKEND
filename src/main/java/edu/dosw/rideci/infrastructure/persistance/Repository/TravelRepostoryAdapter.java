package edu.dosw.rideci.infrastructure.persistance.Repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.events.TravelCreatedEvent;
import edu.dosw.rideci.application.port.out.EventPublisher;
import edu.dosw.rideci.application.port.out.TravelRepositoryPort;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.exceptions.TravelNotFoundException;
import edu.dosw.rideci.infrastructure.persistance.Entity.TravelDocument;
import edu.dosw.rideci.infrastructure.persistance.Repository.mapper.TravelMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TravelRepostoryAdapter implements TravelRepositoryPort {

    private final TravelRepository travelRepository;
    private final TravelMapper travelMapper;
    private final EventPublisher eventPublisher;

    @Override
    public Travel save(Travel travel) {
        TravelDocument document = travelMapper.toDocument(travel);

        TravelDocument savedTravel = travelRepository.save(document);

        TravelCreatedEvent event = TravelCreatedEvent.builder()
                .travelId(travel.getId())
                .driverId(travel.getDriverId())
                .state(travel.getStatus())
                .origin(travel.getOrigin())
                .destiny(travel.getDestiny())
                .passengersId(travel.getPassengersId())
                .travelType(travel.getTravelType())
                .departureDateAndTime(travel.getDepartureDateAndTime())
                .build();

        eventPublisher.publish(event, "travel.created");

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
        actualTravel.setOrigin(travelMapper.toLocationEmbeddable(travel.getOrigin()));
        actualTravel.setDestiny(travelMapper.toLocationEmbeddable(travel.getDestiny()));

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

        if (status.equals(Status.COMPLETED)) {
            TravelCompletedEvent completedEvent = TravelCompletedEvent.builder()
                    .travelId(travelToModifyState.getId())
                    .state(travelToModifyState.getStatus())
                    .build();
            eventPublisher.publish(completedEvent, "travel.completed");
        }

        return travelMapper.toDomain(travelUpdated);

    }

}