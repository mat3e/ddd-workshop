package io.github.mat3e.event.customer;

import java.time.Instant;

public class CustomerCreated implements CustomerEvent {
    private final String customerId;
    private final String name;
    private final String surname;
    private final Instant occurredOn;

    public CustomerCreated(String customerId, String name, String surname) {
        this(customerId, name, surname, Instant.now());
    }

    public CustomerCreated(String customerId, String name, String surname, Instant occurredOn) {
        this.customerId = customerId;
        this.name = name;
        this.surname = surname;
        this.occurredOn = occurredOn;
    }

    @Override
    public String customerId() {
        return customerId;
    }

    public String name() {
        return name;
    }

    public String surname() {
        return surname;
    }

    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
