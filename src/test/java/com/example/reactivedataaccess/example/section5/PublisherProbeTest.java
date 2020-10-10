package com.example.reactivedataaccess.example.section5;

import org.junit.jupiter.api.Test;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.publisher.PublisherProbe;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class PublisherProbeTest {


    @Test
    void wasSubscribedMono() {
        PublisherProbe<Void> probe = PublisherProbe.empty();

        assertThat(probe.wasSubscribed()).isFalse();

        probe.mono().subscribe();

        assertThat(probe.wasSubscribed()).isTrue();
    }

    @Test
    void wasSubscribedNumberMono() {
        PublisherProbe<Void> probe = PublisherProbe.empty();
        Mono<Void> mono = probe.mono();

        assertThat(probe.subscribeCount()).isZero();

        mono.subscribe();
        assertThat(probe.subscribeCount()).isEqualTo(1);

        mono.subscribe();
        assertThat(probe.subscribeCount()).isEqualTo(2);
    }

    @Test
    void assertWasCancelledFlux() {
        PublisherProbe<Void> probe = PublisherProbe.of(Flux.never());
        Disposable d = probe.flux().subscribe();
        assertThatExceptionOfType(AssertionError.class)
                .isThrownBy(probe::assertWasCancelled)
                .withMessage("PublisherProbe should have been cancelled but it wasn't");
//        d.dispose();
        probe.assertWasCancelled();
    }
}
