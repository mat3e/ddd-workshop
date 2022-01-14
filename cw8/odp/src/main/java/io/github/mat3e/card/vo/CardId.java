package io.github.mat3e.card.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public final class CardId implements Serializable {
    private static final long serialVersionUID = 1L;

    public static CardId of(CardNumber number, PersonId personId) {
        return new CardId(number, personId);
    }

    public static CardId of(String number, int personId) {
        return new CardId(new CardNumber(number), new PersonId(personId));
    }

    @NotNull
    @Valid
    private CardNumber number;
    @Valid
    private PersonId personId;

    // JPA
    protected CardId() {
    }

    CardId(CardNumber number, PersonId personId) {
        this.number = number;
        this.personId = personId;
    }

    public CardNumber getNumber() {
        return number;
    }

    public PersonId getPersonId() {
        return personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardId)) return false;
        CardId cardId = (CardId) o;
        return number.equals(cardId.number) && personId.equals(cardId.personId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, personId);
    }
}
