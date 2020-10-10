package com.example.reactivedataaccess.dao;

import com.example.reactivedataaccess.model.Person;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface PersonRepository extends ReactiveMongoRepository<Person, String> {
	Flux<Person> findByFirstNameAndLastName(String firstName, String lastName);
	Flux<Person> findByFirstName(String firstName);
}
