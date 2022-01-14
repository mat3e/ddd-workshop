package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryCardRepository implements CardRepository {
    private final Map<CardId, Card> activeCards = new HashMap<>();

    @Override
    public Optional<Card> findById(CardId id) {
        return Optional.ofNullable(activeCards.get(id));
    }

    @Override
    public boolean existsById(CardId id) {
        return activeCards.containsKey(id);
    }

    @Override
    public void save(Card card) {
        activeCards.put(card.id(), card);
    }
}
