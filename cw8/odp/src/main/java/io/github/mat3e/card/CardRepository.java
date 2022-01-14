package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;
import io.github.mat3e.card.vo.CardSnapshot;
import org.springframework.data.repository.Repository;

import java.util.Optional;

interface CardRepository {
    Optional<Card> findBy(CardId id);

    boolean existsById(CardId id);

    void save(Card card);
}

interface SpringCardRepository extends CardRepository, Repository<CardSnapshot, CardId> {
    default Optional<Card> findBy(CardId id) {
        return findById(id).map(Card::restore);
    }

    default void save(Card card) {
        save(card.getSnapshot());
    }

    Optional<CardSnapshot> findById(CardId id);

    void save(CardSnapshot card);
}
