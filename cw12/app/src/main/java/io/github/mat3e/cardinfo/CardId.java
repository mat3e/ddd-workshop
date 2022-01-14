package io.github.mat3e.cardinfo;

import java.io.Serializable;

class CardId implements Serializable {
    private static final long serialVersionUID = 1L;

    private String number;
    private String name;
    private String surname;

    protected CardId() {
    }

    CardId(String number, String name, String surname) {
        this.number = number;
        this.name = name;
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    void setSurname(String surname) {
        this.surname = surname;
    }
}