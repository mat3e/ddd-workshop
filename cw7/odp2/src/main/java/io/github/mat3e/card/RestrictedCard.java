package io.github.mat3e.card;

import io.github.mat3e.exception.BusinessException;
import io.github.mat3e.card.vo.CardId;

import javax.money.MonetaryAmount;

class RestrictedCard {
    private final CardId id;
    // TODO: unblock method and cannot be unblocked until the debt paid off
    private final MonetaryAmount remainingDebt;

    RestrictedCard(CardId id, MonetaryAmount remainingDebt) {
        this.id = id;
        this.remainingDebt = remainingDebt;
    }

    CardId id() {
        return id;
    }

    static class BlockedCardException extends BusinessException {
        BlockedCardException() {
            super("Cannot perform this operation on a blocked card");
        }
    }
}
