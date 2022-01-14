package io.github.mat3e.card;

import io.github.mat3e.exception.BusinessException;
import io.github.mat3e.card.vo.CardId;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "cards")
class Card {
    @EmbeddedId
    private CardId id;
    @Column(name = "limitation")
    private MonetaryAmount limit;
    private MonetaryAmount balance;

    // JPA
    protected Card() {
    }

    Card(CardId id, MonetaryAmount limit) {
        this.id = id;
        this.limit = limit;
        this.balance = limit;
    }

    CardId id() {
        return id;
    }

    void withdraw(MonetaryAmount money) {
        if (balance.isLessThan(money)) {
            throw new InsufficientBalanceException(money, balance);
        }
        balance = balance.subtract(money);
    }

    MonetaryAmount calculateDebt() {
        return limit.subtract(balance);
    }

    static class InsufficientBalanceException extends BusinessException {
        InsufficientBalanceException(MonetaryAmount tooMany, MonetaryAmount available) {
            super("Cannot withdraw " + tooMany + " when having " + available);
        }
    }

    static class AlreadyActivatedException extends BusinessException {
        public AlreadyActivatedException() {
            super("Card was already activated");
        }
    }
}
