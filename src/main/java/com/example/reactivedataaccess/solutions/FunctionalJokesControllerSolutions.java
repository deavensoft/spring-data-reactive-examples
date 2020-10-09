package com.example.reactivedataaccess.solutions;

import com.example.reactivedataaccess.model.Person;
import com.example.reactivedataaccess.service.JokesService;
import com.example.reactivedataaccess.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@AllArgsConstructor
public class FunctionalJokesControllerSolutions {

	private final JokesService jokesService;
	private final PersonService personService;

	/**
	 * Route config
	 */
	//.andRoute(GET("/jokesforpersons/{firstName}/{lastName}"), handler::getJokeForPersons);

	/**
	 * Get jokes for persons with given first and last name if they exist in the database. If no such persons return HTTP status 404
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getJokeForPersons(ServerRequest request) {
		String firstName = request.pathVariable("firstName");
		String lastName = request.pathVariable("lastName");
		Mono<ServerResponse> notFound = ServerResponse.notFound()
				.build();
		Flux<Person> personFlux = personService.findByFirstnameAndLastname(firstName, lastName);
		return personFlux.hasElements().flatMap(has ->
		{
			if (has) {
				return ServerResponse.ok()
						.contentType(TEXT_EVENT_STREAM)
						.body(personFlux.flatMap(person -> jokesService.getRandomJoke(person.getFirstName(), person.getLastName())), String.class);
			} else {
				return notFound;
			}
		});
	}
}
