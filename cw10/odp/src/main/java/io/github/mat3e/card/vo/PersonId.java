package io.github.mat3e.card.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public final class PersonId {
    public static PersonId of(int id) {
        return new PersonId(id);
    }

    @Column(name = "person_id")
    private int id;

    // JPA
    protected PersonId() {
    }

    PersonId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
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
