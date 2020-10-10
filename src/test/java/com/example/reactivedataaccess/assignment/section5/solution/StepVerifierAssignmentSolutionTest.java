package com.example.reactivedataaccess.assignment.section5.solution;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;
import java.util.Comparator;

class StepVerifierAssignmentSolutionTest {

    // 1. Verify that the Flux emits the double of even numbers between 1 and 10
    @Test
    void doubleEvenNumbers() {
        Flux<Integer> source = Flux.range(1, 10)
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2);

        StepVerifier.create(source)
                .expectNext(4, 8, 12, 16, 20)
                .expectComplete()
                .verify();
    }





    // 2. Verify that error is thrown after 50 elements emitted
    @Test
    void exceptionThrown() {
        Flux<Integer> source = Flux.range(1, 50).concatWith(
                Mono.error(new IllegalArgumentException())
        );

        StepVerifier
                .create(source)
                .expectNextCount(50)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException)
                .verify();
    }





    // 3. Implement Joiner.join() method so that the testPublisher() test passes OK
    class Joiner {
        private final Flux<String> source;
        Joiner(Flux<String> source) {
            this.source = source;
        }
        Mono<String> join() {
            return source.reduce((first, second) -> first + " " + second);
        }
    }

    @Test
    void testPublisher() {
        TestPublisher<String> testPublisher = TestPublisher.<String>create();

        Joiner joiner = new Joiner(testPublisher.flux());
        StepVerifier.create(joiner.join())
                .then(() -> testPublisher.emit("Hello", "Reactive", "Stream"))
                .expectNext("Hello Reactive Stream")
                .verifyComplete();
    }






    // 4. Based on the given Flux, verify the elements being output, considering time component, too.
    @Test
    public void verifyWithVirtualTime() {
        StepVerifier.withVirtualTime(() -> Flux.interval(Duration.ofSeconds(3))
                .take(2)
                .mergeOrderedWith(Flux.just(10L, 11L, 12L), Comparator.naturalOrder())
        )
                .expectSubscription()
                .expectNoEvent(Duration.ofSeconds(3))
                .expectNext(0L)
                .expectNoEvent(Duration.ofSeconds(3))
                .expectNext(1L)
                .expectNext(10L, 11L, 12L)
                .expectComplete()
                .verify();
    }
}
