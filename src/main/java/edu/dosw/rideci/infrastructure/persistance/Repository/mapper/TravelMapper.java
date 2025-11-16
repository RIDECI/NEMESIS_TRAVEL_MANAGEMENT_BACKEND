package edu.dosw.rideci.infrastructure.persistance.Repository.mapper;

import org.mapstruct.Mapper;

import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;
import edu.dosw.rideci.infrastructure.controller.dto.Response.TravelResponse;
import edu.dosw.rideci.infrastructure.persistance.Entity.TravelDocument;

@Mapper(componentModel = "spring")
public interface TravelMapper {

    TravelDocument toDocument(Travel travel);

    Travel toDomain(TravelDocument travel);

    TravelResponse toResponse(Travel travel);

    Travel toDomain(TravelRequest travelRequest);

}
