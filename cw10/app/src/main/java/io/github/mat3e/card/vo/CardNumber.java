package io.github.mat3e.card.vo;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Objects;

@Embeddable
public final class CardNumber {
    public static CardNumber of(String number) {
        return new CardNumber(number);
    }

    // 16 numbers: 1 - major industry identifier, 5-7 - Issuer Identification Number (IIN), last - check digit
    @Column
    @NotNull
    @Pattern(regexp="\\d{16}")
    private String number;

    // JPA
    protected CardNumber() {
    }

    CardNumber(String number) {
        this.number = number;
    }

    public String getValue() {
        return number;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardNumber)) return false;
        var that = (CardNumber) o;
        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}
