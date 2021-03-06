package com.example.reactivedataaccess.assignment.section1.solution;

import com.example.reactivedataaccess.assignment.section1.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class Exercise02Solved {

    // 1. Recommended products for a user:
    //    Subscribe to a source of recommendations for a user.
    //    If error is thrown,
    //    fallback to the source of the last seen products by this user.
    //    If the user did not see any products yet (empty source),
    //    fallback to a source of the best selling products.
    //    Take top 3 products (at max) and output product titles as single List<String> (wrapped in a Mono).
    Mono<List<String>> recommenderProductTitles(Flux<Product> userRecommendations,
                                                Flux<Product> userLastSeenProducts,
                                                Flux<Product> bestSellingProducts) {

        return userRecommendations
                .onErrorResume(e -> userLastSeenProducts)
                .switchIfEmpty(bestSellingProducts)
                .map(Product::getTitle)
                .take(3)
                .collectList();
    }

    // 2. Then run all tests in Exercise02Test - all should pass
}
