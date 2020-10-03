package com.example.reactivedataaccess.example.section1;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class Examples01Flux {
    @Test
    void java8StreamPipeline() {
        // simple list
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);

        // creating stream from list
        Stream<Integer> intStream = intList.stream()
                .filter(i -> i > 2) // filter elements which > 2
                .map(i -> i * i); // convert i into its square

        // collect all the squares into a separate list
        List<Integer> list1 = intStream
                .collect(Collectors.toList());

        // print
        System.out.println(list1);
    }



    @Test
    void java8StreamPipeline_cannotReadStreamMultipleTimes() {
        // simple list
        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);

        // creating stream from list
        Stream<Integer> intStream = intList.stream()
                .filter(i -> i > 2) //filter elements which > 2
                .map(i -> i * i); //convert i into its square

        // collect all the squares into a separate list
        List<Integer> list1 = intStream
                .collect(Collectors.toList());

        // print
        System.out.println(list1);


        List<Integer> list2 = intStream
                .collect(Collectors.toList());
    }




    @Test
    void flux_empty() {
        Flux.empty()
                .subscribe(i -> System.out.println("Received : " + i));
    }



    @Test
    void flux_singleElement() {
        Flux.just(1)
                .subscribe(i -> System.out.println("Received : " + i));
    }



    @Test
    void flux_canBeReadMultipleTimes() {
        Flux<Integer> flux = Flux.just(1);

        //Observer 1
        flux.subscribe(i -> System.out.println("Observer-1 : " + i));
        //Observer 2
        flux.subscribe(i -> System.out.println("Observer-2 : " + i));
    }



    @Test
    void flux_withDelay_andLog() throws Exception {
        System.out.println("Starts");

        // flux emits one element per second
        Flux<Character> flux = Flux.just('a', 'b', 'c', 'd')
//                .log() // -> enable logging to see what's going on !!!!
                .delayElements(Duration.ofSeconds(1));

        // Observer 1 - takes 500ms to process
        flux
                .map(Character::toUpperCase)
                .subscribe(i -> {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {}
                    System.out.println("Observer-1 : " + i);
                });
        // Observer 2 - process immediately
        flux.subscribe(i -> System.out.println("Observer-2 : " + i));

        System.out.println("Ends");

        // Just to block the execution - otherwise the program will end only with start and end messages
        Thread.sleep(10000);
    }



    @Test
    void subscriber_WithErrorAndSuccessCallbacks() {
        Flux.just(1,2,3)
                .map(i -> 10 / i)
                .subscribe(
                        i -> System.out.println("Received :: " + i),
                        err -> System.out.println("Error :: " + err),
                        () -> System.out.println("Successfully completed"));
    }

    @Test
    void subscriber_WithErrorAndSuccessCallbacks_SubscriberInterface() {
        Flux.just(1,2,3)
                .map(i -> 10 / i)
                .subscribe(new Subscriber<>() {

                    private Subscription subscription;

                    @Override
                    public void onSubscribe(Subscription s) {
                        this.subscription = s;
                        subscription.request(1);
                    }

                    @Override
                    public void onNext(Integer i) {
                        System.out.println("Received :: " + i);
                        subscription.request(1);
                    }

                    @Override
                    public void onError(Throwable err) {
                        System.err.println("Error :: " + err);
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("Successfully completed");
                    }
                });
    }



    @Test
    void subscriber_ErrorThrown() {
        Flux.just(1,2,3)
                .map(i -> i / (i-2))
                .subscribe(
                        i -> System.out.println("Received :: " + i),
                        err -> System.out.println("Error :: " + err),
                        () -> System.out.println("Successfully completed"));
    }


    @Test
    void flux_Range() {
        Flux.range(10, 5)
                .map(i -> i * 2)
                .subscribe(System.out::println);
    }

    @Test
    void flux_fromArray() {
        String[] arr = {"Hi", "Hello", "How are you"};

        Flux.fromArray(arr)
                .filter(s -> s.length() > 2)
                .subscribe(i -> System.out.println("Received : " + i));
    }



    @Test
    void flux_fromIterable() {
        List<String> list = Arrays.asList("reactive", "spring");
        Flux<String> stringFlux = Flux.fromIterable(list)
                .map(String::toUpperCase);

        // observer-1
        stringFlux
                .map(String::length)
                .subscribe(i -> System.out.println("Observer-1 :: " + i));

        // observer-2
        stringFlux
                .subscribe(i -> System.out.println("Observer-2 :: " + i));
    }

    @Test
    void flux_fromStream() {
        List<String> list = Arrays.asList("reactive", "spring");
        Flux<String> stringFlux = Flux.fromStream(list.stream())
                .map(String::toUpperCase);

        // observer-1
        stringFlux
                .map(String::length)
                .subscribe(i -> System.out.println("Observer-1 :: " + i));

//        // Take care!!!
//        // observer-2
//        stringFlux
//                .subscribe(i -> System.out.println("Observer-2 :: " + i));
    }


    @Test
    void flux_fromStream_Supplier() {
        List<String> list = Arrays.asList("reactive", "spring");
        Flux<String> stringFlux = Flux.fromStream(() -> list.stream())
                .map(String::toUpperCase);

        // observer-1
        stringFlux
                .map(String::length)
                .subscribe(i -> System.out.println("Observer-1 :: " + i));

        // observer-2
        stringFlux
                .subscribe(i -> System.out.println("Observer-2 :: " + i));

    }

}
