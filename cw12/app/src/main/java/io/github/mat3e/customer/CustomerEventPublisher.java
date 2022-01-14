package io.github.mat3e.customer;

import io.github.mat3e.ddd.DomainEventPublisher;
import io.github.mat3e.event.customer.CustomerCreated;
import io.github.mat3e.event.customer.CustomerDeleted;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

@Component
@RepositoryEventHandler
class CustomerEventPublisher {
    private final DomainEventPublisher publisher;

    CustomerEventPublisher(DomainEventPublisher publisher) {
        this.publisher = publisher;
    }

    @HandleAfterCreate
    void handleCreate(Customer customer) {
        publisher.publish(new CustomerCreated(getId(customer), customer.getName(), customer.getSurname()));
    }

    @HandleAfterDelete
    void handleDelete(Customer customer) {
        publisher.publish(new CustomerDeleted(getId(customer)));
    }

    private String getId(Customer customer) {
        return customer.getPersonId().getId();
    }
}
