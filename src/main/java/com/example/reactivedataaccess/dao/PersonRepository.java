package com.example.reactivedataaccess.dao;

import com.example.reactivedataaccess.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {

}
