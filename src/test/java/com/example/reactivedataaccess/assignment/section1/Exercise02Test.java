package com.example.reactivedataaccess.assignment.section1;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;

class Exercise02Test {

    private Exercise02 exercise;

    @BeforeEach
    void setup() {
        exercise = new Exercise02();
    }

    @Test
    void recommenderProductTitles_WhenHavingUserRecommendation_ShouldReturnTopTitles() {
        Mono<List<String>> result = exercise.recommenderProductTitles(
                Flux.just(
                        new Product(1, "Jeans"),
                        new Product(2, "T-Shirt"),
                        new Product(3, "Skirt"),
                        new Product(4, "Sweater")
                ),
                Flux.empty(),
                Flux.empty()
        );

        assertThat(result.block(), Matchers.contains("Jeans", "T-Shirt", "Skirt"));
    }

    @Test
    void recommenderProductTitles_WhenError_ShouldReturnLastSeenProducts()  {
        Mono<List<String>> result = exercise.recommenderProductTitles(
                Flux.just(new Product(1, "Jeans"))
                        .concatMap(e -> Mono.error(new IllegalAccessException())),
                Flux.just(
                        new Product(10, "Hat"),
                        new Product(11, "Trainers")
                ),
                Flux.empty()
        );

        assertThat(result.block(), Matchers.contains("Hat", "Trainers"));
    }


    @Test
    void recommenderProductTitles_WhenErrorAndEmpyLastProductsSeen_ShouldReturnBestSellingProducts()  {
        Mono<List<String>> result = exercise.recommenderProductTitles(
                Flux.just(new Product(1, "Jeans"))
                        .concatMap(e -> Mono.error(new IllegalAccessException())),
                Flux.empty(),
                Flux.just(
                        new Product(20, "Socks"),
                        new Product(21, "Bags"),
                        new Product(22, "Jackets"),
                        new Product(23, "Boxers")
                )
        );

        assertThat(result.block(), Matchers.contains("Socks", "Bags", "Jackets"));
    }


}
