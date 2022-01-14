package io.github.mat3e.card.vo;

import java.util.Objects;

public final class PersonId {
    public static PersonId of(int id) {
        return new PersonId(id);
    }

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
