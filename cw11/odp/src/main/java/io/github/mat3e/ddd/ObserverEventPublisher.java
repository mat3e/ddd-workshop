package io.github.mat3e.ddd;

import java.util.HashSet;
import java.util.Set;

public class ObserverEventPublisher implements DomainEventPublisher {
    private final Set<EventHandler> handlers = new HashSet<>();

    @Override
    public void publish(final DomainEvent event) {
        handlers.forEach(handler -> handler.handle(event));
    }

    void register(final EventHandler handler) {
        handlers.add(handler);
    }

    public interface EventHandler {
        void handle(DomainEvent event);
    }
}
