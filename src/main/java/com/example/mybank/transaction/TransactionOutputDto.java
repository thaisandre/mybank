package com.example.mybank.transaction;

import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.time.Instant;

class TransactionOutputDto {

    private Instant date;
    private TransactionType type;
    private BigDecimal value;
    private String accountNumber;
    private BigDecimal administrativeTax;

    public TransactionOutputDto(Transaction transaction) {
        Assert.notNull(transaction, "transaction must not be null");
        this.date = transaction.getCreatedAt();
        this.type = transaction.getType();
        this.value = transaction.getValue();
        this.accountNumber = transaction.getClientAccountNumber();
        this.administrativeTax = transaction.getAdministrativeTax();
    }

    public Instant getDate() {
        return date;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public BigDecimal getAdministrativeTax() {
        return administrativeTax;
    }
}
