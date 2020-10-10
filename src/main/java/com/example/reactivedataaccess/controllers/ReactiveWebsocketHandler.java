package com.example.reactivedataaccess.controllers;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component("ReactiveWebSocketHandler")
public class ReactiveWebsocketHandler implements WebSocketHandler {

	@Override
	public Mono<Void> handle(WebSocketSession webSocketSession) {
		return webSocketSession.send(Flux.interval(Duration.ofSeconds(20)).map(t -> "Hello from WS!")
				.map(webSocketSession::textMessage))
				.and(webSocketSession.receive()
				.map(WebSocketMessage::getPayloadAsText).log());
	}
}
