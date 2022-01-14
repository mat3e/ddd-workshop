package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface RestrictedCardRepository extends Repository<RestrictedCard, CardId> {
    Optional<RestrictedCard> findById(CardId id);

    boolean existsById(CardId id);

    void save(RestrictedCard card);
}
