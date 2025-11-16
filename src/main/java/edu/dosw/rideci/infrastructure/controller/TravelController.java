package edu.dosw.rideci.infrastructure.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.dosw.rideci.application.port.in.CreateTravelUseCase;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;
import edu.dosw.rideci.infrastructure.controller.dto.Response.TravelResponse;
import edu.dosw.rideci.infrastructure.persistance.Repository.mapper.TravelMapper;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/travels")
@RequiredArgsConstructor
public class TravelController {

    private final CreateTravelUseCase createTravelUseCase;
    private final TravelMapper travelMapper;

    @PostMapping("")
    public ResponseEntity<TravelResponse> createTravel(@RequestBody TravelRequest travelRequest) {
        Travel travel = travelMapper.toDomain(travelRequest);
        TravelResponse created = travelMapper.toResponse(createTravelUseCase.createTravel(travel));

        return ResponseEntity.status(HttpStatus.CREATED).body(created);

    }
}
