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
import edu.dosw.rideci.application.port.in.GetAllTravelUseCase;
import edu.dosw.rideci.application.port.in.GetTravelUseCase;
import edu.dosw.rideci.application.port.in.ModifyTravelUseCase;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.domain.model.enums.Status;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;
import edu.dosw.rideci.infrastructure.controller.dto.Response.TravelResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
public class TravelController {

    private final CreateTravelUseCase createTravelUseCase;
    private final GetTravelUseCase getTravelUseCase;
    private final DeleteTravelUseCase deleteTravelUseCase;
    private final ModifyTravelUseCase modifyTravelUseCase;
    private final GetAllTravelUseCase getAllTravelUseCase;
    private final ChangeStateTravelUseCase changeStateTravelUseCase;
    private final TravelMapperInitial travelMapper;

    @PostMapping("")
    public ResponseEntity<TravelResponse> createTravel(@RequestBody TravelRequest travelRequest) {
        Travel travel = travelMapper.toDomain(travelRequest);
        TravelResponse created = travelMapper.toResponse(createTravelUseCase.createTravel(travel));

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }

    @PutMapping("/{id}")
    public ResponseEntity<TravelResponse> updateTravel(@PathVariable Long id,
            @RequestBody TravelRequest travelRequest) {

        TravelResponse updated = travelMapper.toResponse(modifyTravelUseCase.updateTravel(id, travelRequest));

        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TravelResponse> getTravelById(@PathVariable Long id) {

        TravelResponse travel = travelMapper.toResponse(getTravelUseCase.getTravelById(id));

        return ResponseEntity.ok(travel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTravelById(@PathVariable Long id) {
        deleteTravelUseCase.deleteTravelById(id);

        return ResponseEntity.noContent().build();

    }

    @GetMapping("/allTravels")
    public ResponseEntity<List<TravelResponse>> getAllTravels() {

        List<Travel> travel = getAllTravelUseCase.getAllTravels();

        List<TravelResponse> travelResponse = travelMapper.toListResponse(travel);

        return ResponseEntity.ok(travelResponse);

    }

    @PatchMapping("/{id}")
    public ResponseEntity<TravelResponse> updateStatusTravel(@PathVariable Long id, @RequestBody Status status) {

        Travel travel = changeStateTravelUseCase.changeStateTravel(id, status);

        TravelResponse travelUpdated = travelMapper.toResponse(travel);

        return ResponseEntity.ok(travelUpdated);

    }
}