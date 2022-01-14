package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface ActiveCardRepository extends Repository<Card, CardId> {
    Optional<Card> findById(CardId id);

    boolean existsById(CardId id);

    void save(Card card);

    void deleteById(CardId id);
}
