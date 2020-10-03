package com.example.reactivedataaccess.service;

import com.example.reactivedataaccess.dao.PersonRepository;
import com.example.reactivedataaccess.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class PersonService {
	private final PersonRepository repository;

	public Flux<Person> listPersons() {
		return repository.findAll().log();
	}

	public Mono<Person> createPerson(Mono<Person> personMono) {
		return personMono.flatMap(repository::save).log();
	}

	public Flux<Person> createPersons(Flux<Person> persons) {
		return persons.flatMap(repository::save);
	}

	public Mono<Person> getPerson(String personId) {
		return this.repository.findById(personId).log();
	}

	public Flux<Person> findByFirstnameAndLastname(String firstName, String lastName) {
		return null;
	}
}
