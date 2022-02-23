package com.example.mybank.client;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.LocalDate;

class NewClientOutputDto {

    private String name;
    private boolean exclusive;
    private BigDecimal balance;
    private String accountNumber;
    private LocalDate birthDate;

    public NewClientOutputDto(Client client) {
        Assert.notNull(client, "client must not be null");
        this.name = client.getName();
        this.exclusive = client.isExclusive();
        this.balance = client.getBalance();
        this.accountNumber = client.getAccountNumber();
        this.birthDate = client.getBirthDate();
    }

    public String getName() {
        return name;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }
}
