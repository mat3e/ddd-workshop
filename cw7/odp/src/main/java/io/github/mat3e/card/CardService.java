package io.github.mat3e.card;

import io.github.mat3e.exception.BusinessException;
import io.github.mat3e.card.vo.CardId;

import javax.money.MonetaryAmount;

public class CardService {
    private final CardRepository repository;

    CardService(CardRepository repository) {
        this.repository = repository;
    }

    public void activate(CardId cardId, MonetaryAmount initialLimit) {
        repository.save(new Card(cardId, initialLimit));
    }

    public void withdraw(MonetaryAmount money, CardId cardId) {
        Card card = cardFor(cardId);
        card.withdraw(money);
        repository.save(card);
    }

    public void block(CardId cardId) {
        Card card = cardFor(cardId);
        card.block();
        repository.save(card);
    }

    private Card cardFor(CardId cardId) {
        return repository.findBy(cardId).orElseThrow(() -> BusinessException.notFound("Credit card"));
    }
}
