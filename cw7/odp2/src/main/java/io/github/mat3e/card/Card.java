package io.github.mat3e.card;

import io.github.mat3e.exception.BusinessException;
import io.github.mat3e.card.vo.CardId;

import javax.money.MonetaryAmount;

class Card {
    private final CardId id;
    private final MonetaryAmount limit;
    private MonetaryAmount balance;

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
            super("Cannot withdraw " + tooMany + " when having just " + available);
        }
    }
}
