package com.example.reactivedataaccess.example.section1;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.adapter.JdkFlowAdapter;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.concurrent.Flow;

class Examples02Mono {
    @Test
    void mono() {
        Mono.just(1)
                .subscribe(i -> System.out.println("Received :: " + i));
    }

    @Test
    void monoAndFluxArePublishers() {
        Publisher<Integer> publisher1 = Mono.just(1);
        Publisher<Integer> publisher2 = Flux.just(1,2,3);

        Flow.Publisher<Integer> flowPublisher1 = JdkFlowAdapter.publisherToFlowPublisher(publisher1);
        Flow.Publisher<Integer> flowPublisher2 = JdkFlowAdapter.publisherToFlowPublisher(publisher2);

        flowPublisher2.subscribe(new Flow.Subscriber<>() {
            private Flow.Subscription subscription;

            @Override
            public void onSubscribe(Flow.Subscription subscription) {
                this.subscription = subscription;
                subscription.request(1);
            }

            @Override
            public void onNext(Integer item) {
                System.out.println("Received :: " + item);
                subscription.request(1);
            }

            @Override
            public void onError(Throwable throwable) {
                throwable.printStackTrace();
            }

            @Override
            public void onComplete() {
                System.out.println("Completed");
            }
        });
    }


    @Test
    void mono_fromCallable() {
        Mono.fromCallable(() -> 1)
            .subscribe(i -> System.out.println("Received :: " + i));

    }


    @Test
    void mono_fromSupplier() {
        Mono.fromSupplier(() -> "a")
            .subscribe(i -> System.out.println("Received :: " + i));

    }


    @Test
    void mono_fromRunnable() {
        Mono.fromRunnable(() -> System.out.println("Hello"))
                .subscribe(i -> System.out.println("Received :: " + i));

    }


    @Test
    void mono_fromRunnable_Extended() {
        Mono.fromRunnable(() -> System.out.println("Hello"))
                .subscribe(
                        i -> System.out.println("Received :: " + i),
                        err -> System.out.println("Error :: " + err),
                        () -> System.out.println("Successfully completed"));
    }


}
