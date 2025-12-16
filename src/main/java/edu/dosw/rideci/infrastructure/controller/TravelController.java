package edu.dosw.rideci.infrastructure.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dosw.rideci.application.mapper.TravelMapperInitial;
import edu.dosw.rideci.application.port.in.ChangeStateTravelUseCase;
import edu.dosw.rideci.application.port.in.CreateTravelUseCase;
import edu.dosw.rideci.application.port.in.DeleteTravelUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelByDriverIdUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelsByOrganizerUseCase;
import edu.dosw.rideci.application.port.in.GetAllTravelsByPassengerIdUseCase;
import edu.dosw.rideci.application.port.in.GetPassengerListUseCase;
import edu.dosw.rideci.application.port.in.UpdateAvailableSlotsUseCase;
import edu.dosw.rideci.application.port.in.GetTravelUseCase;
import edu.dosw.rideci.application.port.in.ModifyTravelUseCase;

import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;
import edu.dosw.rideci.infrastructure.controller.dto.Response.TravelResponse;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
@Tag(name = "Travel Management", description = "API para gestión de viajes en RIDECI")
public class TravelController {

    private final CreateTravelUseCase createTravelUseCase;
    private final GetTravelUseCase getTravelUseCase;
    private final DeleteTravelUseCase deleteTravelUseCase;
    private final ModifyTravelUseCase modifyTravelUseCase;
    private final GetAllTravelUseCase getAllTravelUseCase;
    private final ChangeStateTravelUseCase changeStateTravelUseCase;
    private final GetPassengerListUseCase getPassengerListUseCase;
    private final GetAllTravelByDriverIdUseCase getAllTravelByDriverIdUseCase;
    private final GetAllTravelsByOrganizerUseCase getAllTravelsByOrganizerUseCase;
    private final GetAllTravelsByPassengerIdUseCase getAllTravelsByPassengerIdUseCase;
    private final edu.dosw.rideci.application.usecases.UpdateTravelPassengersUseCase updateTravelPassengersUseCase;
    private final UpdateAvailableSlotsUseCase updateAvailableSlotsUseCase;
    private final TravelMapperInitial travelMapper;

    @PostMapping("")
    public ResponseEntity<TravelResponse> createTravel(
            @Parameter(description = "Datos del viaje a crear", required = true) @RequestBody TravelRequest travelRequest) {
        TravelResponse created = travelMapper
                .toResponse(createTravelUseCase.createTravel(travelRequest));

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelResponse> updateTravel(
            @Parameter(description = "ID del viaje a actualizar", required = true) @PathVariable String id,
            @Parameter(description = "Nuevos datos del viaje", required = true) @RequestBody TravelRequest travelRequest) {

        TravelResponse updated = travelMapper.toResponse(modifyTravelUseCase.updateTravel(id, travelRequest));

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/all")
    public ResponseEntity<List<TravelResponse>> getAllTravels() {
        List<Travel> travel = getAllTravelUseCase.getAllTravels();
        List<TravelResponse> travelResponse = travelMapper.toListResponse(travel);
        return ResponseEntity.ok(travelResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelResponse> getTravelById(
            @Parameter(description = "ID del viaje a buscar", required = true) @PathVariable String id) {

        TravelResponse travel = travelMapper.toResponse(getTravelUseCase.getTravelById(id));
        return ResponseEntity.ok(travel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravelById(
            @Parameter(description = "ID del viaje a eliminar", required = true) @PathVariable String id) {
        deleteTravelUseCase.deleteTravelById(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TravelResponse> updateStatusTravel(
            @Parameter(description = "ID del viaje", required = true) @PathVariable String id,
            @Parameter(description = "Nuevo estado del viaje", required = true) @RequestBody Status status) {

        Travel travel = changeStateTravelUseCase.changeStateTravel(id, status);
        TravelResponse travelUpdated = travelMapper.toResponse(travel);
        return ResponseEntity.ok(travelUpdated);
    }

    @PatchMapping("/{id}/slots")
    public ResponseEntity<Void> updateAvailableSlots(
            @Parameter(description = "ID del viaje", required = true) @PathVariable String id,
            @RequestBody Integer quantity) {
        updateAvailableSlotsUseCase.updateAvailableSlots(id, quantity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/occupantList/{id}")
    public ResponseEntity<List<Long>> getOccupantList(
            @PathVariable String id, @RequestBody List<Long> passengersList) {

        return ResponseEntity.ok(getPassengerListUseCase.getPassengerList(id, passengersList));
    }

    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<TravelResponse>> getAllTravelsByDriverId(
            @PathVariable Long driverId) {
        List<Travel> allTravels = getAllTravelByDriverIdUseCase.getAllTravelsByDriverId(driverId);

        return ResponseEntity.ok(travelMapper.toListResponse(allTravels));
    }

    @GetMapping("/organizer/{organizerId}")
    public ResponseEntity<List<TravelResponse>> getAllTravelsByOrganizerId(
            @PathVariable Long organizerId) {
        List<Travel> allTravelsByOrganizerId = getAllTravelsByOrganizerUseCase.getAllTravelsByOrganizerId(organizerId);

        return ResponseEntity.ok(travelMapper.toListResponse(allTravelsByOrganizerId));
    }

    @GetMapping("/passenger/{passengerId}")
    public ResponseEntity<List<TravelResponse>> getAllTravelsByPassengerId(
            @PathVariable Long passengerId) {
        List<Travel> allTravelsByPassengerId = getAllTravelsByPassengerIdUseCase
                .getAllTravelsByPassengerId(passengerId);

        return ResponseEntity.ok(travelMapper.toListResponse(allTravelsByPassengerId));
    }

    @PatchMapping("/{id}/passengers")
    public ResponseEntity<Void> updatePassengers(
            @PathVariable String id,
            @RequestBody List<Long> passengersIds) {
        updateTravelPassengersUseCase.execute(id, passengersIds);
        return ResponseEntity.ok().build();
    }

}