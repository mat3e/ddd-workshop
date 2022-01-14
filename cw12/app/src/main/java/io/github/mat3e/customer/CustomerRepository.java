package io.github.mat3e.customer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
interface CustomerRepository extends CrudRepository<Customer, PersonId> {
}
