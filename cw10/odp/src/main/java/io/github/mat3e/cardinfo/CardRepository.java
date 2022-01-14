package io.github.mat3e.cardinfo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Optional;

interface CardRepository extends Repository<DummyForRepo, Integer> {
    default Optional<Card> findBy(int id, String number) {
        return findActive(id, number).or(() -> findRestricted(id, number));
    }

    @Query("SELECT new io.github.mat3e.cardinfo.Card(c.id.number.number, p.name, p.surname, c.balance, false)"
            + " FROM Card c, Customer p WHERE c.id.number.number = :num AND c.id.personId.id = p.personId.id AND p.personId.id = :id")
    Optional<Card> findActive(@Param("id") int id, @Param("num") String number);

    @Query("SELECT new io.github.mat3e.cardinfo.Card(c.id.number.number, p.name, p.surname, c.remainingDebt, true)"
            + " FROM RestrictedCard c, Customer p WHERE c.id.number.number = :num AND c.id.personId.id = p.personId.id AND p.personId.id = :id")
    Optional<Card> findRestricted(@Param("id") int id, @Param("num") String number);
}

@Entity
@Table(name = "card_infos")
class DummyForRepo {
    @Id
    private int id;

    protected DummyForRepo() {
    }
}
