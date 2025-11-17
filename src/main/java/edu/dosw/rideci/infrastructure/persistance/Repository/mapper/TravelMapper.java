package edu.dosw.rideci.infrastructure.persistance.Repository.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import edu.dosw.rideci.domain.model.Location;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.infrastructure.persistance.Entity.LocationDocument;
import edu.dosw.rideci.infrastructure.persistance.Entity.TravelDocument;

@Mapper(componentModel = "spring")
public interface TravelMapper {

    TravelDocument toDocument(Travel travel);

    Travel toDomain(TravelDocument travel);

    LocationDocument toLocationEmbeddable(Location location);

    List<Travel> toListDomain(List<TravelDocument> travel);

    List<TravelDocument> toListDocument(List<Travel> travel);

}
