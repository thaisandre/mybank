package com.example.mybank.transaction;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class TransactionOutputDtoTest {

    @Test
    void constructor__shouldThrowExceptionWhenTransactionIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new TransactionOutputDto(null))
                .withMessage("transaction must not be null");
    }

}