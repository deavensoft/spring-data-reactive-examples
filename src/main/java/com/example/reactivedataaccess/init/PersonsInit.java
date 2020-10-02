package com.example.reactivedataaccess.init;

import com.example.reactivedataaccess.dao.PersonRepository;
import com.example.reactivedataaccess.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

@Component
@AllArgsConstructor
public class PersonsInit implements ApplicationRunner {
	private PersonRepository repository;

	@Override
	public void run(ApplicationArguments args) throws Exception {
		repository.deleteAll()
				.thenMany(Flux.just(
						new Person(null, "Petar", "Petrovic"),
						new Person(null, "Nikola", "Milenkovic"),
						new Person(null, "Nikola", "Petkovic"),
						new Person(null, "Marija", "Milovanovic"),
						new Person(null, "Aleksandra", "Ilic"),
						new Person(null, "Petar", "Zivic"),
						new Person(null, "Zorana", "Milenkovic"),
						new Person(null, "Nikola", "Petkovic"),
						new Person(null, "Marija", "Milovanovic"),
						new Person(null, "Aleksandra", "Ilic")))
				.flatMap(repository::save)
				.thenMany(repository.findAll())
				.subscribe(System.out::println);
	}
}
