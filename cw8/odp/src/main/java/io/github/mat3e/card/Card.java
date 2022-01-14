package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;
import io.github.mat3e.card.vo.CardSnapshot;
import io.github.mat3e.exception.BusinessException;

import javax.money.MonetaryAmount;

class Card {
    static Card restore(CardSnapshot snapshot) {
        var result = new Card(snapshot.getId(), snapshot.getLimit());
        result.balance = snapshot.getBalance();
        result.isActive = snapshot.isActive();
        return result;
    }

    private final CardId id;
    private final MonetaryAmount limit;
    private MonetaryAmount balance;
    private boolean isActive = true;

    Card(CardId id, MonetaryAmount limit) {
        this.id = id;
        this.limit = limit;
        this.balance = limit;
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

    CardSnapshot getSnapshot() {
        return new CardSnapshot(id, limit, balance, isActive);
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
