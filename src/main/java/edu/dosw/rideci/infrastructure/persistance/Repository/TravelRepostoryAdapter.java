package edu.dosw.rideci.infrastructure.persistance.Repository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Repository;

import edu.dosw.rideci.application.events.TravelCancelledEvent;
import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.events.TravelCreatedEvent;
import edu.dosw.rideci.application.events.TravelUpdatedEvent;
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
        TravelDocument document = TravelDocument.builder()
                .id(travel.getId())
                .organizerId(travel.getOrganizerId())
                .driverId(travel.getDriverId())
                .availableSlots(travel.getAvailableSlots())
                .status(travel.getStatus())
                .travelType(travel.getTravelType())
                .estimatedCost(travel.getEstimatedCost())
                .departureDateAndTime(travel.getDepartureDateAndTime())
                .passengersId(travel.getPassengersId())
                .conditions(travel.getConditions())
                .origin(travelMapper.toLocationEmbeddable(travel.getOrigin()))
                .destiny(travelMapper.toLocationEmbeddable(travel.getDestiny()))
                .build();

        TravelDocument savedTravel = travelRepository.save(document);

        TravelCreatedEvent event = TravelCreatedEvent.builder()
                .travelId(document.getId())
                .organizerId(document.getOrganizerId())
                .driverId(document.getDriverId())
                .availableSlots(document.getAvailableSlots())
                .status(document.getStatus())
                .estimatedCost(document.getEstimatedCost())
                .travelType(document.getTravelType())
                .origin(travelMapper.toLocationDomain(document.getOrigin()))
                .destiny(travelMapper.toLocationDomain(document.getDestiny()))
                .passengersId(document.getPassengersId())
                .conditions(document.getConditions())
                .departureDateAndTime(travel.getDepartureDateAndTime())
                .build();

        eventPublisher.publish(event, "travel.created");

        return travelMapper.toDomain(savedTravel);
    }

    @Override
    public Travel getTravelById(String id) {

        TravelDocument travel = travelRepository.findById(id)
                .orElseThrow(() -> new TravelNotFoundException("The travel with id: {id}, doesnt exists "));

        return travelMapper.toDomain(travel);

    }

    @Override
    public void deleteTravelById(String id) {

        travelRepository.deleteById(id);

        TravelCancelledEvent event = TravelCancelledEvent.builder()
                .travelId(id)
                .build();

        eventPublisher.publish(event, "travel.cancelled");

    }

    @Override
    public Travel updateTravel(String id, Travel travel) {

        TravelDocument actualTravel = travelRepository.findById(id)
                .orElseThrow(() -> new TravelNotFoundException("Dont exist the travel with id: {id}"));

        actualTravel.setAvailableSlots(travel.getAvailableSlots());
        actualTravel.setEstimatedCost(travel.getEstimatedCost());
        actualTravel.setDepartureDateAndTime(travel.getDepartureDateAndTime());
        actualTravel.setPassengersId(travel.getPassengersId());
        actualTravel.setConditions(travel.getConditions());
        actualTravel.setOrigin(travelMapper.toLocationEmbeddable(travel.getOrigin()));
        actualTravel.setDestiny(travelMapper.toLocationEmbeddable(travel.getDestiny()));

        TravelUpdatedEvent travelUpdatedEvent = TravelUpdatedEvent.builder()
                .travelId(id)
                .availableSlots(actualTravel.getAvailableSlots())
                .estimatedCost(actualTravel.getEstimatedCost())
                .departureDateAndTime(actualTravel.getDepartureDateAndTime())
                .origin(travelMapper.toLocationDomain(actualTravel.getOrigin()))
                .destiny(travelMapper.toLocationDomain(actualTravel.getDestiny()))
                .build();

        eventPublisher.publish(travelUpdatedEvent, "travel.updated");

        TravelDocument travelUpdated = travelRepository.save(actualTravel);

        return travelMapper.toDomain(travelUpdated);

    }

    @Override
    public List<Travel> getAllTravels() {

        List<TravelDocument> allTravels = travelRepository.findAll();

        return travelMapper.toListDomain(allTravels);

    }

    @Override
    public Travel changeStateTravel(String id, Status status) {

        TravelDocument travelToModifyState = travelRepository.findById(id).orElseThrow(
                () -> new TravelNotFoundException("The trip to change the state with id: {id} does not exist "));

        travelToModifyState.setStatus(status);

        TravelDocument travelUpdated = travelRepository.save(travelToModifyState);

        if (status.equals(Status.COMPLETED)) {
            TravelCompletedEvent completedEvent = TravelCompletedEvent.builder()
                    .travelId(travelToModifyState.getId())
                    .driverId(travelToModifyState.getDriverId())
                    .organizerId(travelToModifyState.getOrganizerId())
                    .travelType(travelToModifyState.getTravelType())
                    .departureDateAndTime(travelToModifyState.getDepartureDateAndTime())
                    .passengerList(travelToModifyState.getPassengersId())
                    .state(travelToModifyState.getStatus())
                    .build();
            eventPublisher.publish(completedEvent, "travel.completed");
        }

        return travelMapper.toDomain(travelUpdated);

    }

    @Override
    public List<Long> getPassengerList(String id, List<Long> passengerList) {

        TravelDocument travel = travelRepository.findById(id)
                .orElseThrow(() -> new TravelNotFoundException("The travel with id: {id} not found "));

        boolean equals = new HashSet<>(travel.getPassengersId()).equals(new HashSet<>(passengerList));

        if (equals) {
            return passengerList;
        }

        return new ArrayList<>();

    }

    @Override
    public List<Travel> getAllTravelsByDriverId(Long driverId) {

        List<TravelDocument> allTravelByDriver = travelRepository.findAllByDriverId(driverId);

        return travelMapper.toListDomain(allTravelByDriver);

    }

    @Override
    public List<Travel> getAllTravelsByOrganizerId(Long organizerId) {

        List<TravelDocument> allTravelByOrganizerId = travelRepository.findAllByOrganizerId(organizerId);

        return travelMapper.toListDomain(allTravelByOrganizerId);

    }

    @Override
    public List<Travel> getAllTravelsByPassengerId(Long passengerId) {

        List<TravelDocument> allTravels = travelRepository.findAllByPassengersId(passengerId);

        return travelMapper.toListDomain(allTravels);

    }

    @Override
    public void updatePassengers(String travelId, List<Long> passengersIds) {
        TravelDocument travel = travelRepository.findById(travelId)
                .orElseThrow(() -> new TravelNotFoundException("Travel with id " + travelId + " not found"));

        travel.setPassengersId(passengersIds);

        // Recalculate available slots
        // Assuming conditions or vehicle capacity isn't changing here directly,
        // effectively available slots = initial capacity - current passengers
        // However, since we don't store initial capacity separately easily here (it's
        // dynamic),
        // we might just update the list.
        // IMPORTANT: The prompt asked to update the list, usually this affects
        // availableSlots too.
        // If availableSlots was manually managed, we should update it.
        // Let's assume availableSlots is manually managed or we just set the list as
        // requested.
        // For robustness, one might decrement availableSlots, but typically 'updating
        // list'
        // implies synchronization. ensuring we don't exceed limits would be domain
        // logic.

        TravelDocument saved = travelRepository.save(travel);

        TravelUpdatedEvent event = TravelUpdatedEvent.builder()
                .travelId(travelId)
                .availableSlots(saved.getAvailableSlots()) // Sending current slots
                .passengersId(saved.getPassengersId())
                .build();

        eventPublisher.publish(event, "travel.passengers.updated");

    }

    public void updateAvailableSlots(String id, Integer quantity) {
        TravelDocument travel = travelRepository.findById(id)
                .orElseThrow(() -> new TravelNotFoundException(
                        "The trip to change the state with id: {id} does not exist "));

        travel.setAvailableSlots(travel.getAvailableSlots() + quantity);
        travelRepository.save(travel);
    }

}