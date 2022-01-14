package io.github.mat3e.ddd;

public interface DomainEventPublisher {
    <T extends DomainEvent> void publish(T event);
}
