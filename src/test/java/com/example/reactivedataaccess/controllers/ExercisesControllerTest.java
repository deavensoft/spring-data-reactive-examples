package com.example.reactivedataaccess.controllers;

import com.example.reactivedataaccess.dao.PersonRepository;
import com.example.reactivedataaccess.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ExercisesControllerTest {

	@Autowired
	private WebTestClient client;

	@Autowired
	private PersonRepository repository;

	private List<Person> Persons = Arrays.asList(
			new Person("1", "Petar", "Petrovic"),
			new Person("2", "Zoran", "Milenkovic"),
			new Person("3", "Nikola", "Petkovic"),
			new Person("4", "Marija", "Milovanovic"),
			new Person("5", "Aleksandra", "Ilic"));

	@BeforeEach
	public void setUp() {
		repository.deleteAll()
				.thenMany(Flux.fromIterable(Persons))
				.flatMap(repository::save)
				.doOnNext(System.out::println)
				.then()
				.block();
	}

	@Test
	public void transformMono() {
		Person Person = new Person("1", "Jelena", "Kocic");

		client.post().uri("/exercises/capitalize")
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.accept(MediaType.APPLICATION_JSON_UTF8)
				.body(Mono.just(Person), Person.class)
				.exchange()
				.expectStatus().isOk()
				.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
				.expectBody()
				.jsonPath("$.id").isNotEmpty()
				.jsonPath("$.firstName").isEqualTo("JELENA")
				.jsonPath("$.lastName").isEqualTo("KOCIC")
				.consumeWith(System.out::println);
	}
}
