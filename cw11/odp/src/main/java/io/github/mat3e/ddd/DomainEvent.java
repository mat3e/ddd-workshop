package io.github.mat3e.ddd;

import java.time.Instant;

public interface DomainEvent {
    Instant occurredOn();
}
