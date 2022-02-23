package com.example.mybank.client;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

import static com.fasterxml.jackson.annotation.JsonFormat.Shape.NUMBER_FLOAT;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.STRING;

class NewClientInputDto {

    @NotBlank(message = "{newClientDto.name.not.blank}")
    private String name;

    private boolean exclusive;

    @Positive(message = "{newClientDto.balance.positive}")
    @JsonFormat(shape= NUMBER_FLOAT)
    private BigDecimal balance;

    @NotBlank(message = "{newClientDto.accountNumber.not.blank}")
    private String accountNumber;

    @JsonFormat(shape = STRING, pattern = "yyyy-MM-dd")
    @NotNull(message = "{newClientDto.birthDate.not.null}")
    private LocalDate birthDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isExclusive() {
        return exclusive;
    }

    public void setExclusive(boolean exclusive) {
        this.exclusive = exclusive;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Client toEntity() {
        Client client = new Client(name, accountNumber, birthDate);
        client.deposit(balance);
        client.setExclusive(exclusive);
        return client;
    }
}
