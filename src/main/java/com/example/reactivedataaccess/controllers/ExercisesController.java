package com.example.reactivedataaccess.controllers;

import com.example.reactivedataaccess.model.Person;
import com.example.reactivedataaccess.model.PersonsWithSameName;
import com.example.reactivedataaccess.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@AllArgsConstructor
@RequestMapping("/exercises/")
public class ExercisesController {
	private final PersonService personService;

	private final Flux<String> names = Flux.just("Lazar", "Predrag", "Zvonko", "Mika");

	/**
	 * Capitalize first and last name of the posted person and write it to DB
	 * @return
	 */
	@PostMapping("/capitalize")
	Mono<Person> capitalizePerson(@RequestBody Mono<Person> mono) {
		return null; // FIXME
	}

	/**
	 * Return all persons order by last name in descending order
	 * @return
	 */
	@GetMapping("/ordered")
	Flux<Person> orderedPersons() {
		return null; // FIXME
	}

	/** Return all persons with given first and last name
	 * Note: Extend PersonRepository with findAllByFirstNameAndLastName(String firstName, String lastName) method
	 */
	@GetMapping("/persons")
	Flux<Person> getPersons(String firstName, String lastName) {
		return null; // FIXME
	}

	/**
	 * Using zip operation replace the names of the first 4 persons from the database with values from the names variable
	 */
	@GetMapping("/combine")
	Mono<Void> combinePersons() {
		return null; // FIXME
	}

	/**
	 * Using Flux's groupBy method return Flux of PersonsWithSameName, each containing name and the list of all persons with the same name
	 */
	@GetMapping("/group-by-name")
	Flux<PersonsWithSameName> groupByName() {
		return null; // FIXME
	}
}
