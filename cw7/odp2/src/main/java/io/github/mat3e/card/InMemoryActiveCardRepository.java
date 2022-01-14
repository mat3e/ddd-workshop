package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryActiveCardRepository implements ActiveCardRepository {
    private final Map<CardId, Card> activeCards = new HashMap<>();

    @Override
    public Optional<Card> findBy(CardId id) {
        return Optional.ofNullable(activeCards.get(id));
    }

    @Override
    public void save(Card card) {
        activeCards.put(card.id(), card);
    }

    @Override
    public void delete(CardId id) {
        activeCards.remove(id);
    }
}
