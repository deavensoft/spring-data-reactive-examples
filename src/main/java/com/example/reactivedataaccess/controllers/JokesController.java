package com.example.reactivedataaccess.controllers;

import com.example.reactivedataaccess.service.JokesService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@AllArgsConstructor
@RestController
public class JokesController {

	private final JokesService jokesService;

	/**
	 * Return jokes for all persons in database with given name. If such persons don't exist return a random joke.
	 * Note: Add findAllByFirstName(String firstName) method in PersonRepository and use it.
	 */
	@GetMapping("/joke")
	Flux<String> getPersonJoke(String firstName) {
		return jokesService.getPersonsJokes(firstName);
	}

	/**
	 * Return jokes for all persons in database.
	 */
	@GetMapping("/jokes")
	Flux<String> getAllPersonsJokes() {
		return jokesService.getAllPersonsJokes();
	}

	/**
	 * Fetch a number of jokes from the Jokes API and then return one joke with given delay.
	 */
	@GetMapping(path = "/streamjokes", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> streamJokes(@RequestParam Integer jokesCount, @RequestParam Integer delayInSeconds) {
		return jokesService.getDelayedJokes(jokesCount, delayInSeconds);
	}
}
