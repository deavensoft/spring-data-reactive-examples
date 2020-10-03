package com.example.reactivedataaccess.example.section1;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

class Examples04Backpressure {

    @Test
    void backpressure_Drop() throws Exception {
        Flux<Object> fluxAsyncBackp = Flux.create(emitter -> {

            // Publish 1000 numbers
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                emitter.next(i);
            }
            // When all values or emitted, call complete.
            emitter.complete();
        }, FluxSink.OverflowStrategy.DROP)
                .onBackpressureDrop(i -> System.out.println(Thread.currentThread().getName() + " | DROPPED = " + i));

        fluxAsyncBackp.subscribeOn(Schedulers.elastic()).publishOn(Schedulers.elastic()).subscribe(i -> {
            // Process received value.
            System.out.println(Thread.currentThread().getName() + " | Received = " + i);
            // 500 mills delay to simulate slow subscriber
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


        /*
         * Notice above -
         *
         * OverflowStrategy.DROP - If subscriber can't keep up with values, then drop
         * the values.
         *
         * subscribeOn & publishOn - Put subscriber & publishers on different threads.
         */

        // Since publisher & subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(100000);
    }

    @Test
    void backpressure_Latest() throws Exception {
        Flux<Object> fluxAsyncBackp = Flux.create(emitter -> {

            // Publish 1000 numbers
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                emitter.next(i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // When all values or emitted, call complete.
            emitter.complete();

        }, FluxSink.OverflowStrategy.LATEST);

        fluxAsyncBackp.subscribeOn(Schedulers.elastic()).publishOn(Schedulers.elastic()).subscribe(i -> {
            // Process received value.
            System.out.println(Thread.currentThread().getName() + " | Received = " + i);
            // 100 mills delay to simulate slow subscriber
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }, e -> {
            // Process error
            System.err.println(Thread.currentThread().getName() + " | Error = " + e.getMessage());
        });
        /*
         * Notice above -
         *
         * OverflowStrategy.LATEST - Overwrites values if subscriber can't keep up.
         *
         * subscribeOn & publishOn - Put subscriber & publishers on different threads.
         */

        // Since publisher & subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(100000);
    }

    @Test
    void backpressure_Error() throws Exception {
        Flux<Object> fluxAsyncBackp = Flux.create(emitter -> {

            // Publish 1000 numbers
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                // BackpressureStrategy.ERROR will cause MissingBackpressureException when
                // subscriber can't keep up. So handle exception & call error handler.
                emitter.next(i);
            }
            // When all values or emitted, call complete.
            emitter.complete();

        }, FluxSink.OverflowStrategy.ERROR);

        fluxAsyncBackp.subscribeOn(Schedulers.elastic()).publishOn(Schedulers.elastic()).subscribe(i -> {
            // Process received value.
            System.out.println(Thread.currentThread().getName() + " | Received = " + i);
        }, e -> {
            // Process error
            System.err.println(Thread.currentThread().getName() + " | Error = " + e.getClass().getSimpleName() + " "
                    + e.getMessage());
        });
        /*
         * Notice above -
         *
         * OverflowStrategy.ERROR - Throws MissingBackpressureException is subscriber
         * can't keep up.
         *
         * subscribeOn & publishOn - Put subscriber & publishers on different threads.
         */

        // Since publisher & subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(100000);
    }


    @Test
    void backpressure_Ignore() throws Exception {
        Flux<Object> fluxAsyncBackp = Flux.create(emitter -> {

            // Publish 1000 numbers
            for (int i = 0; i < 1000; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                // BackpressureStrategy.MISSING will cause MissingBackpressureException
                // eventually
                emitter.next(i);
            }
            // When all values or emitted, call complete.
            emitter.complete();

        }, FluxSink.OverflowStrategy.IGNORE);

        fluxAsyncBackp.subscribeOn(Schedulers.elastic()).publishOn(Schedulers.elastic()).subscribe(i -> {
            // Process received value.
            System.out.println(Thread.currentThread().getName() + " | Received = " + i);
        }, e -> {
            // Process error
            System.err.println(Thread.currentThread().getName() + " | Error = " + e.getClass().getSimpleName() + " "
                    + e.getMessage());
        });
        /*
         * Notice above -
         *
         * subscribeOn & publishOn - Put subscriber & publishers on different threads.
         */

        // Since publisher & subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(100000);
    }


    @Test
    void backpressure_Buffer() throws Exception {
        Flux<Object> fluxAsyncBackp = Flux.create(emitter -> {

            // Publish 1000 numbers
            for (int i = 0; i < 999_000_000; i++) {
                System.out.println(Thread.currentThread().getName() + " | Publishing = " + i);
                emitter.next(i);
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            // When all values or emitted, call complete.
            emitter.complete();
        }, FluxSink.OverflowStrategy.BUFFER);

        fluxAsyncBackp.subscribeOn(Schedulers.elastic()).publishOn(Schedulers.elastic()).subscribe(i -> {
            // Process received value.
            System.out.println(Thread.currentThread().getName() + " | Received = " + i);
            // 500 mills delay to simulate slow subscriber
            try {
                Thread.sleep(100);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }, e -> {
            // Process error
            System.err.println(Thread.currentThread().getName() + " | Error = " + e.getClass().getSimpleName() + " "
                    + e.getMessage());
        });
        /*
         * Notice above -
         *
         * subscribeOn & publishOn - Put subscriber & publishers on different threads.
         */

        // Since publisher & subscriber run on different thread than main thread, keep
        // main thread active for 100 seconds.
        Thread.sleep(1000000);
    }
}
