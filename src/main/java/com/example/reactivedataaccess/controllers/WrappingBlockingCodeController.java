package com.example.reactivedataaccess.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RestController
@RequestMapping("/wrap-blocking/")
public class WrappingBlockingCodeController {

    @GetMapping
    public Mono<String> unblockCall() {
        Mono<String> blockingWrapper = Mono.fromCallable(() -> {
            return blockingHeavyCalculation();
        });

        blockingWrapper = blockingWrapper.subscribeOn(Schedulers.boundedElastic());

        return blockingWrapper;
    }

    private String blockingHeavyCalculation() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {}

        return "My heavy calculated result!";
    }
}
