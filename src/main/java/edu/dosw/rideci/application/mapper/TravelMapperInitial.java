package edu.dosw.rideci.application.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import edu.dosw.rideci.domain.model.Destiny;
import edu.dosw.rideci.domain.model.Origin;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.infrastructure.controller.dto.Request.TravelRequest;
import edu.dosw.rideci.infrastructure.controller.dto.Response.DestinyResponse;
import edu.dosw.rideci.infrastructure.controller.dto.Response.OriginResponse;
import edu.dosw.rideci.infrastructure.controller.dto.Response.TravelResponse;

@Mapper(componentModel = "spring")
public interface TravelMapperInitial {

    TravelResponse toResponse(Travel travel);

    Travel toDomain(TravelRequest travelRequest);

    OriginResponse toOriginResponse(Origin origin);

    DestinyResponse toDestinyResponse(Destiny destiny);

    List<TravelResponse> toListResponse(List<Travel> travel);

    List<Travel> toListDomain(List<TravelRequest> travel);

}