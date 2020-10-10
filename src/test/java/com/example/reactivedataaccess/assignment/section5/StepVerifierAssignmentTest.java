package com.example.reactivedataaccess.assignment.section5;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import reactor.test.publisher.TestPublisher;

import java.time.Duration;
import java.util.Comparator;

class StepVerifierAssignmentTest {

    // 1. Verify that the Flux emits the double of even numbers between 1 and 10
    @Test
    void doubleEvenNumbers() {
        Flux<Integer> source = Flux.range(1, 10)
                .filter(n -> n % 2 == 0)
                .map(n -> n * 2);

        // FIXME: Verify Flux source using StepVerifier
    }





    // 2. Verify that error is thrown after 50 elements emitted
    @Test
    void exceptionThrown() {
        Flux<Integer> source = Flux.range(1, 50).concatWith(
                Mono.error(new IllegalArgumentException())
        );

        // FIXME: Verify Flux source using StepVerifier
    }





    // 3. Implement Joiner.join() method so that the testPublisher() test passes OK
    class Joiner {
        private final Flux<String> source;
        Joiner(Flux<String> source) {
            this.source = source;
        }
        Mono<String> join() {
            // FIXME: write the proper reactive pipeline which should convert the input Flux to Mono
            //        and reduce it so that testPublisher() passes OK
            return null;
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
                );

                // FIXME:  Using StepVerify fluent API verify elements being emmitted considering time
                //         Note: You must first verify subscription.
    }
}
