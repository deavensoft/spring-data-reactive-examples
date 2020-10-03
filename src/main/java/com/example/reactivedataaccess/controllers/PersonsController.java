package com.example.reactivedataaccess.controllers;

import com.example.reactivedataaccess.model.Person;
import com.example.reactivedataaccess.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@AllArgsConstructor
@RequestMapping("/persons/")
public class PersonsController {
	private final PersonService personService;

	@GetMapping("/{id}")
	public Mono<ResponseEntity<Person>> getPerson(@PathVariable String id) {
		Mono<Person> personMono = this.personService.getPerson(id);
		return personMono.map(ResponseEntity::ok).defaultIfEmpty(ResponseEntity.notFound().build());
	}

	@GetMapping
	public Flux<Person> getPersons() {
		return personService.listPersons();
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping
	public Mono<Person> createPerson(@RequestBody Mono<Person> person) {
		return personService.createPerson(person);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping("/bulk")
	public Flux<Person> createPersons(@RequestBody Flux<Person> persons) {
		return personService.createPersons(persons);
	}

	@GetMapping(path = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<Person> streamFlux() {
		return personService.listPersons().delayElements(Duration.ofSeconds(1));
	}
}
