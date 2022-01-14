package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;

import java.util.Optional;

interface RestrictedCardRepository {
    Optional<RestrictedCard> findBy(CardId id);

    boolean existsBy(CardId id);

    void save(RestrictedCard card);
}
