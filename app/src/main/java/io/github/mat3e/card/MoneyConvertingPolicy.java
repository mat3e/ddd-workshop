package io.github.mat3e.card;

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import java.math.BigDecimal;

import static org.javamoney.moneta.convert.ConversionOperators.exchange;

@FunctionalInterface
interface MoneyConvertingPolicy {
    MonetaryAmount convertCurrency(MonetaryAmount from, CurrencyUnit to, BigDecimal ratio);
}

class MoneyConvertingPolicyFactory {
    private final static int DEFAULT_CHARGE_PERCENT = 5;

    private final ExistingCustomerRepository customerRepository;

    MoneyConvertingPolicyFactory(ExistingCustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    MoneyConvertingPolicy policyFor(Card card, MonetaryAmount amountToConvert, BigDecimal ratio) {
        // logic, e.g. special treatment for USD, VIP client, huge amount, loyalty points, premium card, etc.
        if (false) {
            return new NoChargeMoneyConvertingPolicy();
        }
        return new ExtraChargeMoneyConvertingPolicy(DEFAULT_CHARGE_PERCENT);
    }

    private static class ExtraChargeMoneyConvertingPolicy implements MoneyConvertingPolicy {
        private final BigDecimal ratioMultiplier;

        ExtraChargeMoneyConvertingPolicy(int percent) {
            if (percent < 1 || percent > 100) {
                throw new IllegalArgumentException("Provide 1-100. Provided: " + percent);
            }
            this.ratioMultiplier = BigDecimal.ONE.add(new BigDecimal(percent / 100.0));
        }

        @Override
        public MonetaryAmount convertCurrency(MonetaryAmount from, CurrencyUnit to, BigDecimal ratio) {
            return from.multiply(ratio.multiply(ratioMultiplier)).with(exchange(to));
        }
    }

    private static class NoChargeMoneyConvertingPolicy implements MoneyConvertingPolicy {
        @Override
        public MonetaryAmount convertCurrency(MonetaryAmount from, CurrencyUnit to, BigDecimal ratio) {
            return from.multiply(ratio).with(exchange(to));
        }
    }
}
