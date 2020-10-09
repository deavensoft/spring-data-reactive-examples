package com.example.reactivedataaccess.configuration;


import com.example.reactivedataaccess.controllers.FunctionalJokesController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class RouterConfig {
	@Bean
	public RouterFunction<ServerResponse> route(FunctionalJokesController handler) {
		return RouterFunctions
				.route(GET("/delayedjokes"), handler::getDelayedJokes);
	}
}

