package com.example.reactivedataaccess.assignment.section1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


class Exercise01Test {

    private Exercise01 exercise;

    @BeforeEach
    void setup() {
        exercise = new Exercise01();
    }

    @Test
    void coldPublisherOfUpperCaseChars_whenEmptyArray_ShouldProduceZeroResults() {
        Flux<Character> characterFlux = exercise.coldPublisherOfUpperCaseChars(new Integer[0]);

        StepVerifier.create(characterFlux)
                .expectComplete()
                .verify();
    }

    @Test
    void coldPublisherOfUpperCaseChars_whenGivenUpperCaseLetters_ShouldProduceAllInOutput() {
        Flux<Character> characterFlux = exercise.coldPublisherOfUpperCaseChars(
                new Integer[]{65, 66, 67, 90});

        StepVerifier.create(characterFlux)
                .expectNext('A')
                .expectNext('B')
                .expectNext('C')
                .expectNext('Z')
                .expectComplete()
                .verify();
    }

    @Test
    void coldPublisherOfUpperCaseChars_whenGivenNumbersAndLetters_ShouldProduceOnlyLetters() {
        Flux<Character> characterFlux = exercise.coldPublisherOfUpperCaseChars(
                new Integer[]{50, 51, 65, 52, 66, 67, 90});

        StepVerifier.create(characterFlux)
                .expectNext('A')
                .expectNext('B')
                .expectNext('C')
                .expectNext('Z')
                .expectComplete()
                .verify();
    }

    @Test
    void coldPublisherOfUpperCaseCharsWithLimit_whenGivenUpperCaseLetters_ShouldProduceLimit() {
        int limit = 3;
        Mono<Character> characterFlux = exercise.coldPublisherOfUpperCaseCharsWithLimit(
                new Integer[]{65, 66, 67, 90}, limit);

        StepVerifier.create(characterFlux)
                .expectNext('C')
                .expectComplete()
                .verify();
    }


    @Test
    void coldPublisherOfUpperCaseChars_whenMultipleSubscribes_EachShouldGetAllElements() throws Exception {
        Flux<Character> characterFlux = exercise.coldPublisherOfUpperCaseChars(
                new Integer[]{65, 66, 67, 90});

        CountDownLatch countDownLatch = new CountDownLatch(2);
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();

        characterFlux.subscribe(
                first::append,
                System.err::println,
                countDownLatch::countDown
        );

        characterFlux.subscribe(
                second::append,
                System.err::println,
                countDownLatch::countDown
        );


        countDownLatch.await();

        assertThat(first.toString(), is("ABCZ"));
        assertThat(second.toString(), is("ABCZ"));
    }


    @Test
    void hotPublisherOfUpperCaseChars_whenGivenUpperCaseLetters_ShouldDelayElements() {
        Duration delay = Duration.ofSeconds(1);

        StepVerifier.withVirtualTime(
                () -> exercise.hotPublisherOfUpperCaseChars( new Integer[]{65, 66, 67, 90}, delay)
        )
                .expectSubscription()
                .expectNoEvent(delay)
                .expectNext('A')
                .expectNoEvent(delay)
                .expectNext('B')
                .expectNoEvent(delay)
                .expectNext('C')
                .expectNoEvent(delay)
                .expectNext('Z')
                .expectComplete()
                .verify();
    }

    @Test
    void hotPublisherOfUpperCaseChars_whenNextSubscriber_ShouldStartWithCurrentElement() throws Exception {
        Duration delay = Duration.ofSeconds(1);

        Flux<Character> characterFlux = exercise.hotPublisherOfUpperCaseChars(new Integer[]{65, 66, 67, 90}, delay);

        CountDownLatch countDownLatch = new CountDownLatch(2);
        StringBuilder first = new StringBuilder();
        StringBuilder second = new StringBuilder();

        characterFlux.subscribe(
                first::append,
                System.err::println,
                countDownLatch::countDown
        );

        Thread.sleep(2500);

        characterFlux.subscribe(
                second::append,
                System.err::println,
                countDownLatch::countDown
        );

        countDownLatch.await();

        assertThat(first.toString(), is("ABCZ"));
        assertThat(second.toString(), is("CZ"));
    }

    @Test
    void sumElementsOnSamePosition_withInputFluxes_ShouldProduceSumFlux() {
        Flux<Integer> sumFlux = exercise.sumElementsOnSamePosition(Flux.just(1, 2, 3), Flux.just(10, 20, 30));

        StepVerifier.create(sumFlux)
                .expectNext(11)
                .expectNext(22)
                .expectNext(33)
                .expectComplete()
                .verify();
    }
}
