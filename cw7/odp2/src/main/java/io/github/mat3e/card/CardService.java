package io.github.mat3e.card;

import io.github.mat3e.exception.BusinessException;
import io.github.mat3e.card.vo.CardId;

import javax.money.MonetaryAmount;

public class CardService {
    private final ActiveCardRepository activeCardRepository;
    private final RestrictedCardRepository restrictedCardRepository;

    CardService(ActiveCardRepository activeCardRepository, RestrictedCardRepository restrictedCardRepository) {
        this.activeCardRepository = activeCardRepository;
        this.restrictedCardRepository = restrictedCardRepository;
    }

    // TODO: should activate work also as "unblock"?
    public void activate(CardId cardId, MonetaryAmount initialLimit) {
        activeCardRepository.save(new Card(cardId, initialLimit));
    }

    public void withdraw(MonetaryAmount money, CardId cardId) {
        Card card = cardFor(cardId);
        card.withdraw(money);
        activeCardRepository.save(card);
    }

    public void block(CardId cardId) {
        Card card = cardFor(cardId);
        restrictedCardRepository.save(new RestrictedCard(cardId, card.calculateDebt()));
        activeCardRepository.delete(cardId);
    }

    private Card cardFor(CardId cardId) {
        if (restrictedCardRepository.existsBy(cardId)) {
            throw new RestrictedCard.BlockedCardException();
        }
        return activeCardRepository.findBy(cardId).orElseThrow(() -> BusinessException.notFound("Credit card"));
    }
}
