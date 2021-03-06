package com.example.mybank.client;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static com.example.mybank.transaction.TransactionType.WITHDRAW;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static java.time.Instant.now;
import static java.time.temporal.ChronoUnit.YEARS;
import static javax.persistence.GenerationType.IDENTITY;
import static org.springframework.util.Assert.*;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt = now();

    @NotNull
    private String name;

    private boolean exclusive;

    @NotNull
    private BigDecimal balance = ZERO.setScale(4, HALF_UP);

    @Column(unique = true)
    private String accountNumber;

    @NotNull
    private LocalDate birthDate;

    @Deprecated
    public Client() {}

    public Client(String name, String accountNumber, LocalDate birthDate) {
        hasText(name, "name must not be null or empty");
        hasText(accountNumber, "accountNumber must not be null or empty");
        notNull(birthDate, "birthDate must not be null");
        isTrue(YEARS.between(birthDate, LocalDate.now()) >= 18, "The client must be over 18 years old");
        this.name = name;
        this.accountNumber = accountNumber;
        this.birthDate = birthDate;
    }

    public void withdraw(BigDecimal value) {
        notNull(value, "value must not be null");
        isTrue(isPositive(value), "value must be a positive number");
        isTrue(isLessThanOrEqualToBalance(value), "value must not be greater than balance");
        BigDecimal tax = WITHDRAW.getTax(this, value);
        balance = balance.subtract(value.add(value.multiply(tax)));
    }

    public void deposit(BigDecimal value) {
        notNull(value, "value must not be null");
        isTrue(isPositive(value), "value must be a positive number");
        balance = balance.add(value);

    }

    public boolean isExclusive() {
        return exclusive;
    }

    public BigDecimal getBalance() {
        return balance.setScale(4, HALF_UP);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    private boolean isPositive(BigDecimal value) {
        return value.compareTo(ZERO.setScale(4, HALF_UP)) > 0;
    }

    private boolean isLessThanOrEqualToBalance(BigDecimal value) {
        return balance.compareTo(value) >= 0;
    }
}
