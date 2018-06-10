package com.wilsonfranca.kalaha.auth.person;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PersonRepository extends MongoRepository<Person, ObjectId> {


    Optional<Person> findByEmail(String email);

    Optional<Person> findByEmailAndPassword(String email, String password);

}
