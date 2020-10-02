package com.example.reactivedataaccess.dao;

import com.example.reactivedataaccess.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class PersonRepositoryTest {
    @Autowired
    private PersonRepository repository;

    private List<Person> Persons = Arrays.asList(
            new Person("1", "James", "Kirk"),
            new Person("2", "Jean-Luc", "Picard"),
            new Person("3", "Benjamin", "Sisko"),
            new Person("4", "Kathryn", "Janeway"),
            new Person("5", "Jonathan", "Archer"));

    @BeforeEach
    public void setUp() throws Exception {
        repository.deleteAll()
                  .thenMany(Flux.fromIterable(Persons))
                  .flatMap(repository::save)
                  .then()
                  .block();
    }

    @Test
    public void save() {
        Person lorca = new Person(null, "Gabriel", "Lorca");
        StepVerifier.create(repository.save(lorca))
                    .expectNextMatches(Person -> !Person.getId().equals(""))
                    .verifyComplete();
    }

    @Test
    public void findAll() {
        StepVerifier.create(repository.findAll())
                    .expectNextCount(5)
                    .verifyComplete();
    }

    @Test
    public void findById() {
        Persons.stream()
                .map(Person::getId)
                .forEach(id -> StepVerifier.create(repository.findById(id))
                                             .expectNextCount(1)
                                             .verifyComplete());
    }

    @Test
    public void findByIdNotExist() {
        StepVerifier.create(repository.findById("xyz"))
                    .verifyComplete();
    }

    @Test
    public void count() {
        StepVerifier.create(repository.count())
                    .expectNext(5L)
                    .verifyComplete();
    }

}
