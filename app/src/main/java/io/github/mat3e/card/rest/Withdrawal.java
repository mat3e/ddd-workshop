package io.github.mat3e.card.rest;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.validation.constraints.Positive;

class Withdrawal {
    @Positive
    private int amount;
    private String currency;

    public int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    void setCurrency(String currency) {
        this.currency = currency;
    }

    boolean isLocal() {
        return currency == null || currency.isBlank();
    }

    MonetaryAmount toMoney() {
        return Monetary.getDefaultAmountFactory().setNumber(getAmount()).setCurrency(currency).create();
    }
}
