package com.example.reactivedataaccess.example.section1;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Stream;

class Examples03ColdHotPublishers {

    private int getDataToBePublished(){
        System.out.println("getDataToBePublished was called");
        return 1;
    }

    @Test
    void mono_coldPublisher() {
        Mono.fromSupplier(this::getDataToBePublished)
                .subscribe(i -> System.out.println("Observer-1 :: " + i));
    }



    private Stream<String> getMovie(){
        System.out.println("Got the movie streaming request");
        return Stream.of(
                "scene 1",
                "scene 2",
                "scene 3",
                "scene 4",
                "scene 5"
        );
    }

    @Test
    void netflux_coldPublisher() throws Exception {
        //our NetFlux streamer
        //each scene will play for 2 seconds
        Flux<String> netFlux = Flux.fromStream(() -> getMovie())
                .delayElements(Duration.ofSeconds(2));

        // John start watching the movie
        netFlux.subscribe(scene -> System.out.println("John are watching " + scene));

        // Jenny join after sometime
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread.sleep(5000);
        netFlux.subscribe(scene -> System.out.println("    Jenny is watching " + scene),
                System.err::println,
                () -> countDownLatch.countDown());

        countDownLatch.await();
    }



    @Test
    void netTv_hotPublisher() throws Exception {
        //our movie theatre
        //each scene will play for 2 seconds
        Flux<String> movieTheatre = Flux.fromStream(() -> getMovie())
                .delayElements(Duration.ofSeconds(2)).share();

        // John start watching the movie
        movieTheatre.subscribe(scene -> System.out.println("John are watching " + scene));

        // Jenny join after sometime
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread.sleep(5000);
        movieTheatre.subscribe(scene -> System.out.println("    Jenny is watching " + scene),
                System.err::println,
                () -> countDownLatch.countDown());

        countDownLatch.await();
    }

}
