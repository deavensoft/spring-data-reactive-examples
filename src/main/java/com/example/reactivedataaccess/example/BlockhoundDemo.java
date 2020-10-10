package com.example.reactivedataaccess.example;

import lombok.extern.slf4j.Slf4j;
import reactor.blockhound.BlockHound;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class BlockhoundDemo {


    public static void main(String[] args) {
        BlockhoundDemo demo = new BlockhoundDemo();

        Integer result = demo.blockingIsAllowed().block();
//        Integer result = demo.blockingIsNotAllowed().block();

        log.info("Returned value :: {}", result);
    }

    public Mono<Integer> blockingIsAllowed() {
        return getBlockingMono().subscribeOn(Schedulers.elastic());
    }

    public Mono<Integer> blockingIsNotAllowed() {
        return getBlockingMono().subscribeOn(Schedulers.parallel());
    }

    private Mono<Integer> getBlockingMono() {
        return Mono.just(1).doOnNext(i -> block());
    }

    private void block() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

//    static {
//        BlockHound.install();
////        Hooks.onOperatorDebug();
//    }
}
