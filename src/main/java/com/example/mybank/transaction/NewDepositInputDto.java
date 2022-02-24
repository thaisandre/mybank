package com.example.mybank.transaction;

import com.example.mybank.client.Client;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

import static com.example.mybank.transaction.TransactionType.DEPOSIT;

class NewDepositInputDto {

    @NotBlank(message = "{newDepositInputDto.accountNumber.not.blank}")
    private String accountNumber;

    @NotNull(message = "{newDepositInputDto.value.not.null}")
    @Positive(message = "{newDepositInputDto.value.positive}")
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
        return new Transaction(DEPOSIT, client, value);
    }
}
