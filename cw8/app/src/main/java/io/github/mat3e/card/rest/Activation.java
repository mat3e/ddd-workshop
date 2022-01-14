package io.github.mat3e.card.rest;

import javax.money.Monetary;
import javax.money.MonetaryAmount;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

class Activation {
    @Positive
    private int limit;
    @NotBlank
    private String currency;

    public int getLimit() {
        return limit;
    }

    void setLimit(int limit) {
        this.limit = limit;
    }

    public String getCurrency() {
        return currency;
    }

    void setCurrency(String currency) {
        this.currency = currency;
    }

    MonetaryAmount toMoney() {
        return Monetary.getDefaultAmountFactory().setNumber(limit).setCurrency(currency).create();
    }
}
