package edu.dosw.rideci.infrastructure.persistance.Repository.mapper;

import org.mapstruct.Mapper;

import edu.dosw.rideci.domain.model.Destiny;
import edu.dosw.rideci.domain.model.Origin;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.infrastructure.persistance.Entity.DestinyDocument;
import edu.dosw.rideci.infrastructure.persistance.Entity.OriginDocument;
import edu.dosw.rideci.infrastructure.persistance.Entity.TravelDocument;

@Mapper(componentModel = "spring")
public interface TravelMapper {

    TravelDocument toDocument(Travel travel);

    Travel toDomain(TravelDocument travel);

    OriginDocument toOriginEmbeddable(Origin origin);

    DestinyDocument toDestinyEmbeddable(Destiny destiny);

}
