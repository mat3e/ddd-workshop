package io.github.mat3e.card.vo;

import javax.money.MonetaryAmount;

public class CardSnapshot {
    private CardId id;
    private MonetaryAmount limit;
    private MonetaryAmount balance;
    private boolean isActive;

    // JPA
    protected CardSnapshot() {
    }

    public CardSnapshot(CardId id, MonetaryAmount limit, MonetaryAmount balance, boolean isActive) {
        this.id = id;
        this.limit = limit;
        this.balance = balance;
        this.isActive = isActive;
    }

    public CardId getId() {
        return id;
    }

    public MonetaryAmount getLimit() {
        return limit;
    }

    public MonetaryAmount getBalance() {
        return balance;
    }

    public boolean isActive() {
        return isActive;
    }
}
