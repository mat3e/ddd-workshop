package io.github.mat3e.event.customer;

import io.github.mat3e.ddd.DomainEvent;

public interface CustomerEvent extends DomainEvent {
    String customerId();
}
