package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class InMemoryRestrictedCardRepository implements RestrictedCardRepository {
    private final Map<CardId, RestrictedCard> restrictedCards = new HashMap<>();

    @Override
    public Optional<RestrictedCard> findById(CardId id) {
        return Optional.ofNullable(restrictedCards.get(id));
    }

    @Override
    public boolean existsById(CardId id) {
        return restrictedCards.containsKey(id);
    }

    @Override
    public void save(RestrictedCard card) {
        restrictedCards.put(card.id(), card);
    }
}
