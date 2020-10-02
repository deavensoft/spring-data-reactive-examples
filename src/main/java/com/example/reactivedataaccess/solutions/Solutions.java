package com.example.reactivedataaccess.solutions;

import com.example.reactivedataaccess.model.Person;
import com.example.reactivedataaccess.model.PersonsWithSameName;
import com.example.reactivedataaccess.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@AllArgsConstructor
public class Solutions {
	private final PersonService personService;

	private final Flux<String> names = Flux.just("Lazar", "Predrag", "Zvonko", "Mika");

	/**
	 * Capitalize first and last name of the posted person and write it to DB
	 * @return
	 */
	@PostMapping("/capitalize")
	Mono<Person> capitalizePerson(@RequestBody Mono<Person> mono) {
		return personService
				.createPerson(mono.map(u -> new Person(null, u.getFirstName().toUpperCase(), u.getLastName().toUpperCase())));
	}

	/* Return all persons with given first and last name
	 * Note: Extend PersonRepository with findAllByFirstNameAndLastName(String firstName, String lastName) method
	 */
	@GetMapping("/persons")
	Flux<Person> getPersons(String firstName, String lastName) {
		return personService.findByFirstnameAndLastname(firstName, lastName);
	}

	/**
	 * Using zip operation replace the names of the first 4 persons from the database with values from the names variable
	 */
	@GetMapping("/combine")
	Mono<Void> combinePersons() {
		Flux<Person> personFlux = personService.listPersons();
		return personService.createPersons(Flux.zip(names, personFlux, (r, p) -> new Person(p.getId(), r, p.getLastName())));
	}

	/**
	 * Using Flux's groupBy method return Flux of PersonsWithSameName containing name and the list of all persons with the same name
	 */
	@GetMapping("/group-by-name")
	Flux<PersonsWithSameName> groupByName() {
		return personService.listPersons()
				.groupBy(Person::getFirstName)
				.flatMap(grouped -> grouped.collectList()
						.map(t -> new PersonsWithSameName(grouped.key(), t)));
	}

}

