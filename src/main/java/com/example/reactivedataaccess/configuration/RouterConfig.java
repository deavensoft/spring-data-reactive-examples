package com.example.reactivedataaccess.configuration;


import com.example.reactivedataaccess.controllers.FunctionalJokesController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RouterConfig {
	@Bean
	public RouterFunction<ServerResponse> route(FunctionalJokesController handler) {
		return RouterFunctions
				.route(GET("/delayedjokes"), handler::getDelayedJokes);
	}
}

