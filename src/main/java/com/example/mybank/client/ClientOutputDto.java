package com.example.mybank.client;

import org.springframework.util.Assert;

import java.time.LocalDate;

class ClientOutputDto {

    private String name;
    private boolean exclusive;
    private String accountNumber;
    private LocalDate birthDate;

    public ClientOutputDto(Client client) {
        Assert.notNull(client, "client must not be null");
        this.name = client.getName();
        this.exclusive = client.isExclusive();
        this.accountNumber = client.getAccountNumber();
        this.birthDate = client.getBirthDate();
    }

    public String getName() {
        return name;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
