package io.github.mat3e.card;

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
        // read aggregate from repo
        // execute command on aggregate
        // save aggregate in repo
    }

    public void block(CardId cardId) {
        // ...
    }
}
