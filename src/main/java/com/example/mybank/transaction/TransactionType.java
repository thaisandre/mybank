package com.example.mybank.transaction;

import com.example.mybank.client.Client;

import java.math.BigDecimal;

import static org.springframework.util.Assert.notNull;

public enum TransactionType {

    WITHDRAW(new AdministrationTax()),
    DEPOSIT(new WithoutTax());

    private Tax tax;

    TransactionType(Tax tax) {
        this.tax = tax;
    }

    public BigDecimal getTax(Client client, BigDecimal value) {
        notNull(client, "client must not be null");
        notNull(value, "value must not be null");
        return tax.getValue(client, value);
    }
}
