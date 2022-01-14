package io.github.mat3e.cardinfo;

import com.fasterxml.jackson.annotation.JsonUnwrapped;

import javax.money.MonetaryAmount;

class Card {
    private CardId id;
    private int balance;
    private boolean restricted;

    protected Card() {
    }

    public Card(String number, String name, String surname, MonetaryAmount balance, boolean restricted) {
        this.id = new CardId(number, name, surname);
        this.balance = balance.getNumber().intValue() * (restricted ? -1 : 1);
        this.restricted = restricted;
    }

    @JsonUnwrapped
    public CardId getId() {
        return id;
    }

    void setId(CardId id) {
        this.id = id;
    }

    public int getBalance() {
        return balance;
    }

    void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isRestricted() {
        return restricted;
    }

    void setRestricted(boolean restricted) {
        this.restricted = restricted;
    }
}
