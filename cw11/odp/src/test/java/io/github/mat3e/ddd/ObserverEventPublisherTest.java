package io.github.mat3e.ddd;

import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

class ObserverEventPublisherTest {
    private static final DomainEvent DUMMY_EVENT = Instant::now;

    @Test
    void publish_notifies_registeredHandlers() {
        // given
        var toTest = new ObserverEventPublisher();
        // and
        var handler = new AllEventsHandler();
        toTest.register(handler);
        // and
        var handler2 = new EventsWithDataHandler();
        toTest.register(handler2);

        // when
        toTest.publish(DUMMY_EVENT);

        // then
        assertThat(handler.countInteractions()).isOne();
        assertThat(handler2.countInteractions()).isOne();
        assertThat(handler2.getLastResult()).isZero();

        // when
        int data = 99;
        toTest.publish(new EventWithData(data));

        // then
        assertThat(handler.countInteractions()).isEqualTo(2);
        assertThat(handler2.countInteractions()).isEqualTo(2);
        assertThat(handler2.getLastResult()).isEqualTo(data);
    }

    static class AllEventsHandler implements ObserverEventPublisher.EventHandler {
        private int interactions;

        @Override
        public void handle(DomainEvent event) {
            interactions++;
        }

        int countInteractions() {
            return interactions;
        }
    }

    static class EventsWithDataHandler implements ObserverEventPublisher.EventHandler {
        private int interactions;
        private int lastResult;

        @Override
        public void handle(DomainEvent event) {
            interactions++;
            if (event instanceof EventWithData) {
                lastResult = ((EventWithData) event).data();
            }
        }

        int countInteractions() {
            return interactions;
        }

        int getLastResult() {
            return lastResult;
        }
    }

    static class EventWithData implements DomainEvent {
        private final Instant occurrence = Instant.now();
        private final int data;

        EventWithData(int data) {
            this.data = data;
        }

        @Override
        public Instant occurredOn() {
            return occurrence;
        }

        int data() {
            return data;
        }
    }
}