package edu.dosw.rideci.infrastructure.persistance.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import edu.dosw.rideci.infrastructure.persistance.Entity.TravelDocument;

public interface TravelRepository extends MongoRepository<TravelDocument, String> {

}
