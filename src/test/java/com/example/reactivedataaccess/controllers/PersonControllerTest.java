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
public class PersonControllerTest {
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
    public void testGetAllPersons() {
        client.get().uri("/persons/")
              .accept(MediaType.APPLICATION_JSON_UTF8)
              .exchange()
              .expectStatus().isOk()
              .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
              .expectBodyList(Person.class)
              .hasSize(5)
              .consumeWith(System.out::println);
    }

    @Test
    public void testGetPerson() {
        client.get().uri("/persons/{id}", Persons.get(0).getId())
              .exchange()
              .expectStatus().isOk()
              .expectBody(Person.class)
              .consumeWith(System.out::println);
    }

    @Test
    public void testCreatePerson() {
        Person Person = new Person("1", "Petar", "Petrovic");

        client.post().uri("/persons/")
              .contentType(MediaType.APPLICATION_JSON_UTF8)
              .accept(MediaType.APPLICATION_JSON_UTF8)
              .body(Mono.just(Person), Person.class)
              .exchange()
              .expectStatus().isCreated()
              .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
              .expectBody()
              .jsonPath("$.id").isNotEmpty()
              .jsonPath("$.firstName").isEqualTo("Petar")
              .jsonPath("$.lastName").isEqualTo("Petrovic")
              .consumeWith(System.out::println);
    }
}
