package io.github.mat3e.card.vo;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public final class CardId {
    public static CardId of(CardNumber number) {
        return new CardId(number);
    }

    public static CardId of(String number) {
        return new CardId(new CardNumber(number));
    }

    @NotNull
    @Valid
    private final CardNumber number;

    CardId(CardNumber number) {
        this.number = number;
    }

    public CardNumber getNumber() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardId)) return false;
        CardId cardId = (CardId) o;
        return number.equals(cardId.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
