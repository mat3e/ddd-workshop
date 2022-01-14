package io.github.mat3e.card;

import io.github.mat3e.card.vo.PersonId;

import java.util.ArrayList;
import java.util.List;

class InMemoryCustomerRepository implements ExistingCustomerRepository {
    private final List<PersonId> customers = new ArrayList<>();

    @Override
    public boolean existsById(PersonId id) {
        return customers.contains(id);
    }

    @Override
    public void save(ExistingCustomer customer) {
        customers.add(customer.getId());
    }

    @Override
    public void deleteById(PersonId id) {
        customers.remove(id);
    }
}
