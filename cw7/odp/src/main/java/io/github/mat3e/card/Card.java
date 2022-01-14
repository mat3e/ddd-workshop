package io.github.mat3e.card;

import io.github.mat3e.exception.BusinessException;
import io.github.mat3e.card.vo.CardId;

import javax.money.MonetaryAmount;

class Card {
    private final CardId id;
    private MonetaryAmount limit;
    private boolean isActive = true;

    Card(CardId id, MonetaryAmount limit) {
        this.id = id;
        this.limit = limit;
    }

    CardId id() {
        return id;
    }

    void withdraw(MonetaryAmount money) {
        assertActive();
        if (limit.isLessThan(money)) {
            throw new InsufficientBalanceException(money, limit);
        }
        limit = limit.subtract(money);
    }

    void block() {
        assertActive();
        isActive = false;
    }

    // TODO: we could assume this is already an ActiveCard aggregate and app method "block" should remove card from repo
    private void assertActive() {
        if (!isActive) {
            throw new BlockedCardException();
        }
    }

    static class InsufficientBalanceException extends BusinessException {
        InsufficientBalanceException(MonetaryAmount tooMany, MonetaryAmount available) {
            super("Cannot withdraw " + tooMany + " when having just " + available);
        }
    }

    static class BlockedCardException extends BusinessException {
        BlockedCardException() {
            super("Cannot perform this operation on a blocked card");
        }
    }
}
