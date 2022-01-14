package io.github.mat3e.card;

import io.github.mat3e.card.vo.PersonId;
import org.springframework.data.repository.Repository;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

interface ExistingCustomerRepository extends Repository<ExistingCustomer, PersonId> {
    default void save(PersonId id) {
        save(new ExistingCustomer(id));
    }

    void save(ExistingCustomer customer);

    boolean existsById(PersonId id);

    void deleteById(PersonId id);
}

@Entity
@Table(name = "existing_customers")
class ExistingCustomer {
    @EmbeddedId
    private PersonId id;

    protected ExistingCustomer() {
    }

    ExistingCustomer(PersonId id) {
        this.id = id;
    }

    PersonId getId() {
        return id;
    }
}
