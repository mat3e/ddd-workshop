package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryCardRepository implements CardRepository {
    private final Map<CardId, Card> map = new HashMap<>();

    @Override
    public Optional<Card> findBy(CardId id) {
        return Optional.ofNullable(map.get(id));
    }

    @Override
    public void save(Card card) {
        map.put(card.id(), card);
    }
}
