package com.example.reactivedataaccess.solutions;

import com.example.reactivedataaccess.dao.PersonRepository;
import com.example.reactivedataaccess.model.JokeResponse;
import com.example.reactivedataaccess.model.Person;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;

@Service
@AllArgsConstructor
public class JokesServiceSolutions {

	public static final String ICNDB_COM_BASE_API_ENDPOINT = "http://api.icndb.com";
	public static final String JOKES_PATH = "/jokes";
	public static final String RANDOM_JOKES_PATH = JOKES_PATH + "/random";
	public static final String DO_NOT_HTML_ESCAPE = "escape=javascript";
	// Fetching a random joke
	public static final String RANDOM_JOKE_ENDPOINT = RANDOM_JOKES_PATH;

	// Fetching multiple random jokes
	public static final String RANDOM_JOKES_ENDPOINT = JOKES_PATH + "/{number}";

	// Fetching a specific joke
	public static final String SPECIFIC_JOKE_ENDPOINT = JOKES_PATH + "/{id}";

	// Fetching the number of jokes
	public static final String JOKES_COUNT_ENDPOINT = JOKES_PATH + "/count";

	// Fetching the list of joke categories
	public static final String JOKES_CATEGORIES_ENDPOINT = JOKES_PATH + "/categories";

	// Changing the name of the main character
	public static final String CUSTOM_CHARACTER_ENDPOINT = "firstName={firstName}&lastName={lastName}";

	// Limiting categories
	public static final String CUSTOM_CATEGORIES_ENDPOINT = "limitTo=[{commaSeparatedListOfCategories}]";

	// Exclude jokes from certain categories
	public static final String EXCLUDE_CATEGORIES_ENDPOINT = "exclude=[{commaSeparatedListOfCategories}]";

	private final List<String> jokesCategories = Arrays.asList("explicit", "nerdy");

	private final PersonRepository personRepository;

	public String appendQueryParameters(String baseUrl, String... params) {
		return String.format("%s?%s", baseUrl, String.join("&", params));
	}

	public Flux<String> getJokes(String path, Object... queryParameters) {
		WebClient client = WebClient.create(ICNDB_COM_BASE_API_ENDPOINT);
		return client.get()
				.uri(path, queryParameters)
				.accept(APPLICATION_JSON)
				.retrieve()
				.bodyToFlux(JokeResponse.class)
				.map(jokeResponse -> jokeResponse.getValue().getJoke());
	}

	public Flux<String> getRandomJoke() {
		return getJokes(appendQueryParameters(RANDOM_JOKES_PATH, CUSTOM_CATEGORIES_ENDPOINT, DO_NOT_HTML_ESCAPE), "nerdy");
	}

	public Flux<String> getRandomJoke(String first, String last) {
		return getJokes(appendQueryParameters(RANDOM_JOKES_PATH, CUSTOM_CATEGORIES_ENDPOINT, CUSTOM_CHARACTER_ENDPOINT, DO_NOT_HTML_ESCAPE), "nerdy", first, last);
	}

	//TODO

	/**
	 * Return jokes for all persons in database with given name. If such persons don't exist return a random joke.
	 */
	public Flux<String> getPersonsJokes(String firstName) {
		Flux<Person> personFlux = personRepository.findByFirstName(firstName);
		return personFlux
				.flatMap(person -> getRandomJoke(person.getFirstName(), person.getLastName()))
				.map(joke -> joke + " ")
				.switchIfEmpty(getRandomJoke());
	}

	/**
	 * Return jokes for all persons in database with given name. If such persons don't exist return a random joke.
	 */
	public Flux<String> getAllPersonsJokes() {
		Flux<Person> personFlux = personRepository.findAll();
		return personFlux
				.flatMap(person -> getRandomJoke(person.getFirstName(), person.getLastName()))
				.map(joke -> joke + " ");
	}

	/**
	 * Fetch a number of jokes from the Jokes API and then return one joke with given delay.
	 */
	public Flux<String> getDelayedJokes(Integer jokesCount, Integer delayInSeconds) {
		return getJokes(appendQueryParameters(RANDOM_JOKES_PATH), jokesCount.toString())
				.delayElements(Duration.ofSeconds(delayInSeconds));
	}

}
