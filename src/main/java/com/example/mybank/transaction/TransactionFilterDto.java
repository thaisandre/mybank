package com.example.mybank.transaction;

import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

class TransactionFilterDto {

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "{transactionFilterDto.fromDate.not.null}")
    @PastOrPresent(message = "{transactionFilterDto.fromDate.not.future}")
    private LocalDate fromDate;

    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "{transactionFilterDto.fromDate.not.null}")
    private LocalDate toDate;

    private TransactionType type;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }
}
