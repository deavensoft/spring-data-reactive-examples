package com.example.reactivedataaccess.assignment.section1.solution;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class Exercise01Solved {

    // 1. Given the Array of Integers, create a cold publisher
    //    which converts Integer to a Char representation (ASCII table)
    //    but ignores chars which are not uppercase alphabet chars (65-90 in ASCII table)
    Flux<Character> coldPublisherOfUpperCaseChars(Integer[] inputList) {
        return Flux.fromArray(inputList)
            .filter(i -> i >= 65 && i <= 90)
                .map(i -> (char)i.intValue());
    }

    // 2. Run next tests from Exercise01Test - all should pass:
    //    coldPublisherOfUpperCaseChars_whenEmptyArray_ShouldProduceZeroResults
    //    coldPublisherOfUpperCaseChars_whenGivenUpperCaseLetters_ShouldProduceAllInOutput
    //    coldPublisherOfUpperCaseChars_whenGivenNumbersAndLetters_ShouldProduceOnlyLetters
    //    coldPublisherOfUpperCaseChars_whenMultipleSubscribes_EachShouldGetAllElements


    // 3. Use the Flux publisher from the point 1.
    //    but emit at most "limit" elements
    //    and return the last element
    //
    //    - afterwards run test from Exercise01Test which should pass:
    //      coldPublisherOfUpperCaseCharsWithLimit_whenGivenUpperCaseLetters_ShouldProduceLimit
    Mono<Character> coldPublisherOfUpperCaseCharsWithLimit(Integer[] inputList, int limit) {
        // return coldPublisherOfUpperCaseChars(inputList) ... what next?
        return coldPublisherOfUpperCaseChars(inputList)
                .take(limit)
                .last();
    }


    // 4. Use the Flux publisher from the point 1.
    //    but emit each element by the given delay (e.g. 1 sec)
    //    and make this publisher a hot publisher
    Flux<Character> hotPublisherOfUpperCaseChars(Integer[] inputList, Duration delay) {
        // return coldPublisherOfUpperCaseChars(inputList) ... what next?
        return coldPublisherOfUpperCaseChars(inputList)
                .delayElements(delay)
                .share();
    }


    // 5. Run next tests from Exercise01Test - all should pass:
    //    hotPublisherOfUpperCaseChars_whenGivenUpperCaseLetters_ShouldDelayElements
    //    hotPublisherOfUpperCaseChars_whenNextSubscriber_ShouldStartWithCurrentElement



    // 6. Given the two publishers (Fluxes) of Integer numbers (of the same size)
    //    produce elements which represent the sum of numbers
    //    at the same position in the initial Fluxes
    //    e.g. Flux1 - [1, 2, 3]
    //         Flux2 - [10, 20, 30]
    //         Result= [11, 22, 33]
    //
    //    Then run test in Exercise01Test - is should pass:
    //
    Flux<Integer> sumElementsOnSamePosition(Flux<Integer> firstFlux, Flux<Integer> secondFlux) {
        return firstFlux.zipWith(secondFlux, Integer::sum);
    }
}
