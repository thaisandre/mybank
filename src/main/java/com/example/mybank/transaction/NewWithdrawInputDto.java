package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import static com.example.mybank.transaction.TransactionType.WITHDRAW;
import static com.fasterxml.jackson.annotation.JsonFormat.Shape.NUMBER_FLOAT;

class NewWithdrawInputDto {

    @NotBlank(message = "{newWithdrawInputDto.account.number.not.blank}")
    private String accountNumber;

    @JsonFormat(shape= NUMBER_FLOAT)
    @NotNull(message = "{newWithdrawInputDto.value.not.null}")
    @Positive(message = "{newWithdrawInputDto.value.positive}")
    private BigDecimal value;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Transaction toEntity(Client client) {
        return new Transaction(WITHDRAW, client, value);
    }
}
