package com.example.reactivedataaccess.assignment.section1;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

public class Exercise01 {

    // 1. Given the Array of Integers, create a cold publisher
    //    which converts Integer to a Char representation (ASCII table)
    //    but ignores chars which are not uppercase alphabet chars (65-90 in ASCII table)
    Flux<Character> coldPublisherOfUpperCaseChars(Integer[] inputList) {
        return null; // FIXME
    }

    // 2. Run next tests from Exercise01Test - all should pass:
    //    coldPublisherOfUpperCaseChars_whenEmptyArray_ShouldProduceZeroResults
    //    coldPublisherOfUpperCaseChars_whenGivenUpperCaseLetters_ShouldProduceAllInOutput
    //    coldPublisherOfUpperCaseChars_whenGivenNumbersAndLetters_ShouldProduceOnlyLetters
    //    coldPublisherOfUpperCaseChars_whenMultipleSubscribes_EachShouldGetAllElements



    // 3. Subscribe to the defined Flux publisher "characterFlux"
    //    and print to the console all the Characters from the reactive pipeline.
    //
    //    Subscribe with another subscriber to the same Flux
    //    skip the first 5 elements being emitter,
    //    lowercase the characters
    //    and print in the console all the Characters from the reactive pipeline
    //
    //    Run the test Exercise01Test.subscribeAndPrint_ShouldPrint() and observe the console.
    //    You should get in the console:
    //       H
    //       E
    //       L
    //       L
    //       O
    //       W
    //       O
    //       R
    //       L
    //       D
    //       w
    //       o
    //       r
    //       l
    //       d
    void subscribeAndPrint() {
        Flux<Character> characterFlux = coldPublisherOfUpperCaseChars(
                new Integer[]{72, 69, 76, 76, 79, 32, 87, 79, 82, 76, 68, 33});

        // TODO
    }

    // 4. Use the Flux publisher from the point 1.
    //    but emit at most "limit" elements
    //    and return the last element
    //
    //    - afterwards run test from Exercise01Test which should pass:
    //      coldPublisherOfUpperCaseCharsWithLimit_whenGivenUpperCaseLetters_ShouldProduceLimit
    Mono<Character> coldPublisherOfUpperCaseCharsWithLimit(Integer[] inputList, int limit) {
        // return coldPublisherOfUpperCaseChars(inputList) ... what next?
        return null; // FIXME
    }


    // 5. Use the Flux publisher from the point 1.
    //    but emit each element by the given delay (e.g. 1 sec)
    //    and make this publisher a hot publisher
    Flux<Character> hotPublisherOfUpperCaseChars(Integer[] inputList, Duration delay) {
        // return coldPublisherOfUpperCaseChars(inputList) ... what next?
        return null; // FIXME
    }


    // 6. Run next tests from Exercise01Test - all should pass:
    //    hotPublisherOfUpperCaseChars_whenGivenUpperCaseLetters_ShouldDelayElements
    //    hotPublisherOfUpperCaseChars_whenNextSubscriber_ShouldStartWithCurrentElement



    // 7. Given the two publishers (Fluxes) of Integer numbers (of the same size)
    //    produce elements which represent the sum of numbers
    //    at the same position in the initial Fluxes
    //    e.g. Flux1 - [1, 2, 3]
    //         Flux2 - [10, 20, 30]
    //         Result= [11, 22, 33]
    //
    //    Then run test in Exercise01Test - it should pass:
    //    sumElementsOnSamePosition_withInputFluxes_ShouldProduceSumFlux
    Flux<Integer> sumElementsOnSamePosition(Flux<Integer> firstFlux, Flux<Integer> secondFlux) {
        return null; // FIXME
    }



    // 8. Give the list of cars of the different brands (see class Car)
    //    create a Flux out of the input list,
    //    group the elements by Car.brand
    //    and as a result emit List<Car> for each brand
    //
    //    Hint: You'll need to use flatMap() and convert the Flux to List
    //
    //    Then run test in Exercise01Test - it should pass:
    //    groupCarsByBrand_WithInput_ShouldEmitListsOfSameBrandCars
    Flux<List<Car>> groupCarsByBrand(List<Car> cars) {
        return null; // FIXME
    }
}
