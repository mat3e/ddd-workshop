package io.github.mat3e.card;

import io.github.mat3e.exception.BusinessException;
import io.github.mat3e.card.vo.CardId;

import javax.money.MonetaryAmount;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "restricted_cards")
class RestrictedCard {
    @EmbeddedId
    private CardId id;
    private MonetaryAmount remainingDebt;

    // JPA
    protected RestrictedCard() {
    }

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
