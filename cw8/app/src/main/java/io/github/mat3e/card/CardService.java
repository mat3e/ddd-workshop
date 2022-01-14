package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;
import io.github.mat3e.exception.BusinessException;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public class CardService {
    private final CardRepository cardRepository;

    CardService(CardRepository cardRepository) {
        this.cardRepository = cardRepository;
    }

    public void activate(CardId cardId, MonetaryAmount initialLimit) {
        if (cardRepository.existsById(cardId)) {
            throw new Card.AlreadyActivatedException();
        }
        cardRepository.save(new Card(cardId, initialLimit));
    }

    public void withdraw(int amount, CardId cardId) {
        Card card = cardFor(cardId);
        CurrencyUnit currency = card.calculateDebt().getCurrency();
        MonetaryAmount money = Monetary.getDefaultAmountFactory()
                .setNumber(amount)
                .setCurrency(currency)
                .create()
                .with(Monetary.getDefaultRounding());
        card.withdraw(money);
        cardRepository.save(card);
    }

    public void withdraw(MonetaryAmount money, CardId cardId) {
        Card card = cardFor(cardId);
        card.withdraw(money);
        cardRepository.save(card);
    }

    public void block(CardId cardId) {
        Card card = cardFor(cardId);
        card.block();
        cardRepository.save(card);
    }

    private Card cardFor(CardId cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> BusinessException.notFound("Credit card"));
    }
}
