package io.github.mat3e.customer;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
final class PersonId implements Serializable {
    private static final long serialVersionUID = 1L;

    @JsonCreator
    public static PersonId of(String id) {
        return new PersonId(Integer.parseInt(id));
    }

    @Column
    @JsonValue
    private int id;

    // JPA
    protected PersonId() {
    }

    PersonId(int id) {
        this.id = id;
    }

    public String getId() {
        return "" + id;
    }

    @Override
    public String toString() {
        return "" + id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PersonId)) return false;
        PersonId personId = (PersonId) o;
        return id == personId.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
