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
    private boolean isActive = true;

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
        assertActive();
        if (balance.isLessThan(money)) {
            throw new InsufficientBalanceException(money, balance);
        }
        balance = balance.subtract(money);
    }

    void block() {
        assertActive();
        isActive = false;
    }

    private void assertActive() {
        if (!isActive) {
            throw new BlockedCardException();
        }
    }

    MonetaryAmount calculateDebt() {
        return limit.subtract(balance);
    }

    static class InsufficientBalanceException extends BusinessException {
        InsufficientBalanceException(MonetaryAmount tooMany, MonetaryAmount available) {
            super("Cannot withdraw " + tooMany + " when having " + available);
        }
    }

    static class BlockedCardException extends BusinessException {
        BlockedCardException() {
            super("Cannot perform this operation on a blocked card");
        }
    }

    static class AlreadyActivatedException extends BusinessException {
        public AlreadyActivatedException() {
            super("Card was already activated");
        }
    }
}
