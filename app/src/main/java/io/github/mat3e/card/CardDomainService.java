package io.github.mat3e.card;

import javax.money.MonetaryAmount;
import java.math.BigDecimal;

class CardDomainService {
    private final MoneyConvertingPolicyFactory conversionFactory;

    CardDomainService(MoneyConvertingPolicyFactory conversionFactory) {
        this.conversionFactory = conversionFactory;
    }

    void withdraw(Card card, MonetaryAmount money, BigDecimal ratio) {
        if (card.currency().equals(money.getCurrency())) {
            card.withdraw(money);
            return;
        }
        MoneyConvertingPolicy convertingPolicy = conversionFactory.policyFor(card, money, ratio);
        card.withdraw(convertingPolicy.convertCurrency(money, card.currency(), ratio));
    }
}
