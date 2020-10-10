package com.example.reactivedataaccess.example;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.tools.agent.ReactorDebugAgent;

class DebugInfo {

    public static void main(String[] args) {

        try { imperative(); System.out.println("RE RUN imperative()"); } catch (Exception t) { t.printStackTrace();}
//        try { reactive(); System.out.println("RE RUN reactive()"); } catch (Exception t) { t.printStackTrace();}
//        try { reactiveNoSubscribeOn(); System.out.println("RE RUN reactiveNoSubscribeOn()"); } catch (Exception t) { t.printStackTrace();}
//        try { log(); System.out.println("RE RUN log()"); } catch (Exception t) { t.printStackTrace();}
//        try { hook(); System.out.println("RE RUN hook()"); } catch (Exception t) { t.printStackTrace();}
//        try { checkpoint(); System.out.println("RE RUN checkpoint()"); } catch (Exception t) { t.printStackTrace();}
//
//        try { reactorAgent(); System.out.println("RE RUN hook()"); } catch (Exception t) { t.printStackTrace();}

        System.exit(0);
    }

    private static void imperative() throws ExecutionException, InterruptedException {
        final ScheduledExecutorService executor =
                Executors.newSingleThreadScheduledExecutor();

        int seconds = LocalTime.now().getSecond();
        List<Integer> source;
        if (seconds % 2 == 0) {
            source = IntStream.range(1, 11).boxed().collect(Collectors.toList());
        }
        else if (seconds % 3 == 0) {
            source = IntStream.range(0, 4).boxed().collect(Collectors.toList());
        }
        else {
            source = Arrays.asList(1, 2, 3, 4);
        }

        executor.submit(() -> source.get(5))  //line 76
                .get();
    }

    private static void reactive() {
        int seconds = LocalTime.now().getSecond();
        Mono<Integer> source;
        if (seconds % 2 == 0) {
            source = Flux.range(1, 10)
                    .elementAt(5);
        }
        else if (seconds % 3 == 0) {
            source = Flux.range(0, 4)
                    .elementAt(5);
        }
        else {
            source = Flux.just(1, 2, 3, 4)
                    .elementAt(5);
        }

        source.subscribeOn(Schedulers.parallel())
                .block(); //line 97
    }

    private static void reactiveNoSubscribeOn() {
        int seconds = LocalTime.now().getSecond();
        Mono<Integer> source;
        if (seconds % 2 == 0) {
            source = Flux.range(1, 10)
                    .elementAt(5);
        }
        else if (seconds % 3 == 0) {
            source = Flux.range(0, 4)
                    .elementAt(5);
        }
        else {
            source = Flux.just(1, 2, 3, 4)
                    .elementAt(5);
        }

        source.block(); //line 116
    }

    private static void log() {
        int seconds = LocalTime.now().getSecond();
        Mono<Integer> source;
        if (seconds % 2 == 0) {
            source = Flux.range(1, 10)
                    .elementAt(5)
                    .log("source A");
        }
        else if (seconds % 3 == 0) {
            source = Flux.range(0, 4)
                    .elementAt(5)
                    .log("source B");
        }
        else {
            source = Flux.just(1, 2, 3, 4)
                    .elementAt(5)
                    .log("source C");
        }

        source.block(); //line 138
    }


    private static void hook() {
        Hooks.onOperatorDebug();
        try {
            int seconds = LocalTime.now().getSecond();
            Mono<Integer> source;
            if (seconds % 2 == 0) {
                source = Flux.range(1, 10)
                        .elementAt(5); //line 149
            }
            else if (seconds % 3 == 0) {
                source = Flux.range(0, 4)
                        .elementAt(5); //line 153
            }
            else {
                source = Flux.just(1, 2, 3, 4)
                        .elementAt(5); //line 157
            }

            source.block(); //line 160
        }
        finally {
            Hooks.resetOnOperatorDebug();
        }
    }


    private static void checkpoint() {
        int seconds = LocalTime.now().getSecond();
        Mono<Integer> source;
        if (seconds % 2 == 0) {
            source = Flux.range(1, 10)
                    .elementAt(5)
                    .checkpoint("source range(1,10)");
        }
        else if (seconds % 3 == 0) {
            source = Flux.range(0, 4)
                    .elementAt(5)
                    .checkpoint("source range(0,4)");
        }
        else {
            source = Flux.just(1, 2, 3, 4)
                    .elementAt(5)
                    .checkpoint("source just(1,2,3,4)");
        }

        source.block(); //line 186
    }


//    static {
//        ReactorDebugAgent.init();
//        ReactorDebugAgent.processExistingClasses();
//    }

    private static void reactorAgent() {
        int seconds = LocalTime.now().getSecond();
        Mono<Integer> source;
        if (seconds % 2 == 0) {
            source = Flux.range(1, 10)
                    .elementAt(5); //line 149
        }
        else if (seconds % 3 == 0) {
            source = Flux.range(0, 4)
                    .elementAt(5); //line 153
        }
        else {
            source = Flux.just(1, 2, 3, 4)
                    .elementAt(5); //line 157
        }

        source.block(); //line 160
    }

}
