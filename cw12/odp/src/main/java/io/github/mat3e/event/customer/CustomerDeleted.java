package io.github.mat3e.event.customer;

import java.time.Instant;

public class CustomerDeleted implements CustomerEvent {
    private final String customerId;
    private final Instant occurredOn;

    public CustomerDeleted(String customerId) {
        this(customerId, Instant.now());
    }

    public CustomerDeleted(String customerId, Instant occurredOn) {
        this.customerId = customerId;
        this.occurredOn = occurredOn;
    }

    @Override
    public String customerId() {
        return customerId;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
