package io.github.mat3e.card;

import io.github.mat3e.card.vo.CardId;
import io.github.mat3e.card.vo.PersonId;
import io.github.mat3e.event.customer.CustomerCreated;
import io.github.mat3e.event.customer.CustomerDeleted;
import io.github.mat3e.event.customer.CustomerEvent;
import io.github.mat3e.exception.BusinessException;
import org.springframework.context.event.EventListener;

import javax.money.CurrencyUnit;
import javax.money.Monetary;
import javax.money.MonetaryAmount;

public class CardService {
    private final ActiveCardRepository activeCardRepository;
    private final RestrictedCardRepository restrictedCardRepository;
    private final ExistingCustomerRepository existingCustomerRepository;

    CardService(ActiveCardRepository activeCardRepository, RestrictedCardRepository restrictedCardRepository, ExistingCustomerRepository existingCustomerRepository) {
        this.activeCardRepository = activeCardRepository;
        this.restrictedCardRepository = restrictedCardRepository;
        this.existingCustomerRepository = existingCustomerRepository;
    }

    @EventListener
    public void applyCustomerInfo(CustomerEvent event) {
        var id = PersonId.of(event.customerId());
        if (event instanceof CustomerCreated) {
            existingCustomerRepository.save(id);
            return;
        }
        if (event instanceof CustomerDeleted) {
            existingCustomerRepository.deleteById(id);
        }
    }

    public void activate(CardId cardId, MonetaryAmount initialLimit) {
        if (activeCardRepository.existsById(cardId) || restrictedCardRepository.existsById(cardId)) {
            throw new Card.AlreadyActivatedException();
        }
        if (!existingCustomerRepository.existsById(cardId.getPersonId())) {
            throw BusinessException.notFound("Customer with id " + cardId.getPersonId().getId());
        }
        activeCardRepository.save(new Card(cardId, initialLimit));
    }

    public void withdraw(int amount, CardId cardId) {
        Card card = cardFor(cardId);
        CurrencyUnit currency = card.currency();
        MonetaryAmount money = Monetary.getDefaultAmountFactory()
                .setNumber(amount)
                .setCurrency(currency)
                .create()
                .with(Monetary.getDefaultRounding());
        card.withdraw(money);
        activeCardRepository.save(card);
    }

    public void withdraw(MonetaryAmount money, CardId cardId) {
        Card card = cardFor(cardId);
        card.withdraw(money);
        activeCardRepository.save(card);
    }

    public void block(CardId cardId) {
        Card card = cardFor(cardId);
        restrictedCardRepository.save(new RestrictedCard(cardId, card.calculateDebt()));
        activeCardRepository.deleteById(cardId);
    }

    private Card cardFor(CardId cardId) {
        if (restrictedCardRepository.existsById(cardId)) {
            throw new RestrictedCard.BlockedCardException();
        }
        return activeCardRepository.findById(cardId).orElseThrow(() -> BusinessException.notFound("Credit card"));
    }
}
