package io.github.mat3e.customer;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "customers")
class Customer {
    @Valid
    @EmbeddedId
    private PersonId personId;
    @NotBlank
    private String name;
    @NotBlank
    private String surname;
    @Email
    @NotNull
    private String mail;

    protected Customer() {
    }

    public PersonId getPersonId() {
        return personId;
    }

    void setPersonId(PersonId personId) {
        this.personId = personId;
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

    public String getMail() {
        return mail;
    }

    void setMail(String mail) {
        this.mail = mail;
    }
}
