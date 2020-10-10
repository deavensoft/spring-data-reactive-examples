package com.example.reactivedataaccess.controllers;

import com.example.reactivedataaccess.model.Joke;
import com.example.reactivedataaccess.service.JokesService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import reactor.core.publisher.Flux;

import java.time.Duration;

@RequiredArgsConstructor
@Controller
public class RSocketJokesController {

	private final JokesService jokesService;

	@MessageMapping("rsocketjokes")
	public Flux<Joke> getJokes(String firstName) {
		return Flux.interval(Duration.ofSeconds(5)).map(t -> new Joke("Hello from RSocket"));
	}
}
