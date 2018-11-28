package com.animals.repositories;


import com.animals.models.Animal;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface AnimalsRepository extends MongoRepository<Animal, String> {

    Optional<Animal> findById(ObjectId id);
}