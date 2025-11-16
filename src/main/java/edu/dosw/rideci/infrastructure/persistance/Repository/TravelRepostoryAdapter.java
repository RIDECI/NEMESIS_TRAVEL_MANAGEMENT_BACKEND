package edu.dosw.rideci.infrastructure.persistance.Repository;

import org.springframework.stereotype.Repository;

import edu.dosw.rideci.application.port.out.TravelRepositoryPort;
import edu.dosw.rideci.domain.model.Travel;
import edu.dosw.rideci.infrastructure.persistance.Entity.TravelDocument;
import edu.dosw.rideci.infrastructure.persistance.Repository.mapper.TravelMapper;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TravelRepostoryAdapter implements TravelRepositoryPort {

    private final TravelRepository travelRepository;
    private final TravelMapper travelMapper;

    @Override
    public Travel save(Travel travel) {
        TravelDocument document = travelMapper.toDocument(travel);

        TravelDocument savedTravel = travelRepository.save(document);

        return travelMapper.toDomain(savedTravel);
    }

}
