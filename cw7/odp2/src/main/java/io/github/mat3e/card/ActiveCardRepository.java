package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;

import java.util.Optional;

interface ActiveCardRepository {
    Optional<Card> findBy(CardId id);

    void save(Card card);

    void delete(CardId id);
}
