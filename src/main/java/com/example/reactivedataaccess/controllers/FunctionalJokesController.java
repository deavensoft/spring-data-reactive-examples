package com.example.reactivedataaccess.controllers;

import com.example.reactivedataaccess.service.JokesService;
import com.example.reactivedataaccess.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.http.MediaType.TEXT_EVENT_STREAM;

@AllArgsConstructor
@Component
public class FunctionalJokesController {

	private final JokesService jokesService;
	private final PersonService personService;

	/**
	 * Fetch a number of jokes from the Jokes API and then return one joke with given delay.
	 */
	public Mono<ServerResponse> getDelayedJokes(ServerRequest request) {
		Integer jokesCount = Integer.valueOf(request.queryParam("jokesCount").orElse("5"));
		Integer delayInSeconds = Integer.valueOf(request.queryParam("delayInSeconds").orElse("5"));

		Flux<String> delayedJokes = jokesService.getDelayedJokes(jokesCount, delayInSeconds);

		return ServerResponse.ok()
				.contentType(TEXT_EVENT_STREAM)
				.body(delayedJokes, String.class);
	}

	/**
	 * Get jokes for persons with given first and last name if they exist in the database. If no such persons return HTTP status 404
	 * @param request
	 * @return
	 */
	public Mono<ServerResponse> getJokeForPersons(ServerRequest request) {
		return null;
	}
}
