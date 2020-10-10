package com.example.reactivedataaccess.example;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;

@Slf4j
public class DebuggingReactivePipeline {

    public static void main(String[] args) throws Exception {
        Flux.just(1,2,3,4,5,6)
                .map(n -> n * 2)
                .zipWith(Flux.just("A", "B", "C", "D", "E", "F"))
                .subscribe(t -> log.info("{}#{}", t.getT1(), t.getT2()));

//        Flux.just(1,2,3,4,5,6)
//                .map(n -> n - 3)
//                .map(n -> 10 / n)
//                .onErrorResume(t -> Flux.just(55, 66, 77, 88, 99))
//                .zipWith(Flux.just("A", "B", "C", "D", "E", "F"))
//                .subscribe(t -> log.info("{} # {}", t.getT1(), t.getT2()));

        Thread.sleep(2000);
    }
}
