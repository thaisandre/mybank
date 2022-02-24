package com.example.mybank.transaction;

import com.example.mybank.client.Client;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.*;
import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Instant createdAt = Instant.now();

    @ManyToOne
    private Client client;

    @Enumerated(STRING)
    private TransactionType type;

    private BigDecimal value = ZERO.setScale(4, HALF_UP);
    private BigDecimal administrativeTax = ZERO.setScale(4, HALF_UP);

    @Deprecated
    public Transaction() {}

    public Transaction(TransactionType type, Client client, BigDecimal value) {
        notNull(type, "type must not be null");
        notNull(client, "client must not be null");
        notNull(value, "value must not be null");
        isTrue(value.compareTo(ZERO) > 0, "value must be a positive number");
        this.type = type;
        this.client = client;
        this.value = value;
        this.administrativeTax = type.getTax(client, value);
    }

    public Long getId() {
        return id;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public TransactionType getType() {
        return type;
    }

    public BigDecimal getValue() {
        return value;
    }

    public String getClientAccountNumber() {
        return client.getAccountNumber();
    }

    public BigDecimal getAdministrativeTax() {
        return administrativeTax;
    }
}
