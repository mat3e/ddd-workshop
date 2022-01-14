package io.github.mat3e.card.rest;

import javax.validation.constraints.Positive;

class LocalWithdrawal {
    @Positive
    private int amount;

    public int getAmount() {
        return amount;
    }

    void setAmount(int amount) {
        this.amount = amount;
    }
}
