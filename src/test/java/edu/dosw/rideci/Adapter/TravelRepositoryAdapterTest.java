package edu.dosw.rideci.Adapter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import edu.dosw.rideci.application.events.TravelCompletedEvent;
import edu.dosw.rideci.application.events.TravelCreatedEvent;
import edu.dosw.rideci.application.port.out.EventPublisher;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.domain.model.enums.TravelType;
import edu.dosw.rideci.exceptions.TravelNotFoundException;
import edu.dosw.rideci.infrastructure.persistance.Entity.TravelDocument;
import edu.dosw.rideci.infrastructure.persistance.Repository.TravelRepository;
import edu.dosw.rideci.infrastructure.persistance.Repository.TravelRepostoryAdapter;
import edu.dosw.rideci.infrastructure.persistance.Repository.mapper.TravelMapper;

@ExtendWith(MockitoExtension.class)
class TravelRepositoryAdapterTest {

    @Mock
    private TravelRepository travelRepository;

    @Mock
    private TravelMapper travelMapper;

    @Mock
    private EventPublisher eventPublisher;

    @InjectMocks
    private TravelRepostoryAdapter travelRepositoryAdapter;

    private Travel travelDomain;
    private TravelDocument travelDocument;
    private TravelDocument travelDocumentSaved;

    LocalDateTime departureDate = LocalDateTime.of(2025, 12, 20, 14, 30, 0);

    @BeforeEach
    void setup() {
        travelDomain = Travel.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(3)
                .status(Status.ACTIVE)
                .estimatedCost(20.5)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking")
                .origin(null)
                .destiny(null)
                .build();

        travelDocument = TravelDocument.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(3)
                .status(Status.ACTIVE)
                .estimatedCost(20.5)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking")
                .origin(null)
                .destiny(null)
                .build();

        travelDocumentSaved = TravelDocument.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(3)
                .status(Status.ACTIVE)
                .estimatedCost(20.5)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking")
                .origin(null)
                .destiny(null)
                .build();
    }

    @DisplayName("Should save a travel and publish TravelCreatedEvent")
    @Test
    void shouldSaveTravelSuccessfully() {

        when(travelMapper.toLocationEmbeddable(any())).thenReturn(null);
        when(travelRepository.save(any(TravelDocument.class))).thenReturn(travelDocumentSaved);
        when(travelMapper.toLocationDomain(any())).thenReturn(null);
        when(travelMapper.toDomain(any(TravelDocument.class))).thenReturn(travelDomain);

        Travel result = travelRepositoryAdapter.save(travelDomain);

        assertNotNull(result);
        assertEquals("550e8400-e29b-41d4-a716-446655440000", result.getId());
        assertEquals(10L, result.getDriverId());
        assertEquals(Status.ACTIVE, result.getStatus());
        assertEquals(3, result.getAvailableSlots());

        verify(travelRepository, times(1)).save(any(TravelDocument.class));
        verify(eventPublisher, times(1)).publish(any(TravelCreatedEvent.class), eq("travel.created"));
    }

    @DisplayName("Should retrieve a travel by ID")
    @Test
    void shouldGetTravelByIdSuccessfully() {
        when(travelRepository.findById("550e8400-e29b-41d4-a716-446655440000")).thenReturn(Optional.of(travelDocument));
        when(travelMapper.toDomain(any(TravelDocument.class))).thenReturn(travelDomain);

        Travel result = travelRepositoryAdapter.getTravelById("550e8400-e29b-41d4-a716-446655440000");

        assertNotNull(result);
        assertEquals("550e8400-e29b-41d4-a716-446655440000", result.getId());
        assertEquals(10L, result.getDriverId());

        verify(travelRepository, times(1)).findById("550e8400-e29b-41d4-a716-446655440000");
        verify(travelMapper, times(1)).toDomain(any(TravelDocument.class));
    }

    @DisplayName("Should throw TravelNotFoundException when travel ID does not exist")
    @Test
    void shouldThrowExceptionWhenTravelNotFound() {

        when(travelRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        assertThrows(TravelNotFoundException.class, () -> {
            travelRepositoryAdapter.getTravelById("non-existent-id");
        });

        verify(travelRepository, times(1)).findById("non-existent-id");
    }

    @DisplayName("Should delete a travel by ID")
    @Test
    void shouldDeleteTravelByIdSuccessfully() {
        travelRepositoryAdapter.deleteTravelById("550e8400-e29b-41d4-a716-446655440000");

        verify(travelRepository, times(1)).deleteById("550e8400-e29b-41d4-a716-446655440000");
    }

    @DisplayName("Should update a travel successfully")
    @Test
    void shouldUpdateTravelSuccessfully() {
        Travel updatedTravel = Travel.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(5)
                .status(Status.ACTIVE)
                .estimatedCost(25.0)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L, 4L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking allowed")
                .origin(null)
                .destiny(null)
                .build();

        TravelDocument updatedDocument = TravelDocument.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(5)
                .status(Status.ACTIVE)
                .estimatedCost(25.0)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L, 4L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking allowed")
                .origin(null)
                .destiny(null)
                .build();

        when(travelRepository.findById("550e8400-e29b-41d4-a716-446655440000")).thenReturn(Optional.of(travelDocument));
        when(travelRepository.save(any(TravelDocument.class))).thenReturn(updatedDocument);
        when(travelMapper.toLocationEmbeddable(null)).thenReturn(null);
        when(travelMapper.toDomain(any(TravelDocument.class))).thenReturn(updatedTravel);

        Travel result = travelRepositoryAdapter.updateTravel("550e8400-e29b-41d4-a716-446655440000", updatedTravel);

        assertNotNull(result);
        assertEquals("550e8400-e29b-41d4-a716-446655440000", result.getId());
        assertEquals(5, result.getAvailableSlots());
        assertEquals(25.0, result.getEstimatedCost());
        assertEquals("No smoking allowed", result.getConditions());

        verify(travelRepository, times(1)).findById("550e8400-e29b-41d4-a716-446655440000");
        verify(travelRepository, times(1)).save(any(TravelDocument.class));
    }

    @DisplayName("Should throw TravelNotFoundException when updating non-existent travel")
    @Test
    void shouldThrowExceptionWhenUpdatingNonExistentTravel() {
        when(travelRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        assertThrows(TravelNotFoundException.class, () -> {
            travelRepositoryAdapter.updateTravel("non-existent-id", travelDomain);
        });

        verify(travelRepository, times(1)).findById("non-existent-id");
        verify(travelRepository, never()).save(any(TravelDocument.class));
    }

    @DisplayName("Should retrieve all travels")
    @Test
    void shouldGetAllTravelsSuccessfully() {
        List<TravelDocument> travelDocumentList = List.of(travelDocument);
        List<Travel> expectedTravelList = List.of(travelDomain);

        when(travelRepository.findAll()).thenReturn(travelDocumentList);
        when(travelMapper.toListDomain(any())).thenReturn(expectedTravelList);

        List<Travel> result = travelRepositoryAdapter.getAllTravels();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("550e8400-e29b-41d4-a716-446655440000", result.get(0).getId());

        verify(travelRepository, times(1)).findAll();
        verify(travelMapper, times(1)).toListDomain(travelDocumentList);
    }

    @DisplayName("Should change travel state to COMPLETED and publish TravelCompletedEvent")
    @Test
    void shouldChangeStateTravelToCompletedSuccessfully() {
        Travel completedTravel = Travel.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(3)
                .status(Status.COMPLETED)
                .estimatedCost(20.5)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking")
                .origin(null)
                .destiny(null)
                .build();

        TravelDocument completedDocument = TravelDocument.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(3)
                .status(Status.COMPLETED)
                .estimatedCost(20.5)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking")
                .origin(null)
                .destiny(null)
                .build();

        when(travelRepository.findById("550e8400-e29b-41d4-a716-446655440000")).thenReturn(Optional.of(travelDocument));
        when(travelRepository.save(any(TravelDocument.class))).thenReturn(completedDocument);
        when(travelMapper.toDomain(any(TravelDocument.class))).thenReturn(completedTravel);

        Travel result = travelRepositoryAdapter.changeStateTravel("550e8400-e29b-41d4-a716-446655440000",
                Status.COMPLETED);

        assertNotNull(result);
        assertEquals(Status.COMPLETED, result.getStatus());

        verify(travelRepository, times(1)).findById("550e8400-e29b-41d4-a716-446655440000");
        verify(travelRepository, times(1)).save(any(TravelDocument.class));
        verify(eventPublisher, times(1)).publish(any(TravelCompletedEvent.class), eq("travel.completed"));
    }

    @DisplayName("Should change travel state to CANCELLED without publishing event")
    @Test
    void shouldChangeStateTravelToCancelledSuccessfully() {
        Travel cancelledTravel = Travel.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(3)
                .status(Status.CANCELLED)
                .estimatedCost(20.5)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking")
                .origin(null)
                .destiny(null)
                .build();

        TravelDocument cancelledDocument = TravelDocument.builder()
                .id("550e8400-e29b-41d4-a716-446655440000")
                .driverId(10L)
                .availableSlots(3)
                .status(Status.CANCELLED)
                .estimatedCost(20.5)
                .departureDateAndTime(departureDate)
                .passengersId(List.of(2L, 3L))
                .travelType(TravelType.TRIP)
                .conditions("No smoking")
                .origin(null)
                .destiny(null)
                .build();

        when(travelRepository.findById("550e8400-e29b-41d4-a716-446655440000")).thenReturn(Optional.of(travelDocument));
        when(travelRepository.save(any(TravelDocument.class))).thenReturn(cancelledDocument);
        when(travelMapper.toDomain(any(TravelDocument.class))).thenReturn(cancelledTravel);

        Travel result = travelRepositoryAdapter.changeStateTravel("550e8400-e29b-41d4-a716-446655440000",
                Status.CANCELLED);

        assertNotNull(result);
        assertEquals(Status.CANCELLED, result.getStatus());

        verify(travelRepository, times(1)).findById("550e8400-e29b-41d4-a716-446655440000");
        verify(travelRepository, times(1)).save(any(TravelDocument.class));
        verify(eventPublisher, never()).publish(any(TravelCompletedEvent.class), eq("travel.completed"));
    }

    @DisplayName("Should throw TravelNotFoundException when changing state of non-existent travel")
    @Test
    void shouldThrowExceptionWhenChangingStateOfNonExistentTravel() {
        when(travelRepository.findById("non-existent-id")).thenReturn(Optional.empty());

        assertThrows(TravelNotFoundException.class, () -> {
            travelRepositoryAdapter.changeStateTravel("non-existent-id", Status.COMPLETED);
        });

        verify(travelRepository, times(1)).findById("non-existent-id");
        verify(travelRepository, never()).save(any(TravelDocument.class));
    }
}