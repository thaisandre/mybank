package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.example.mybank.transaction.TransactionType.WITHDRAW;
import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

class TransactionTypeTest {

    @Test
    void getTax__shouldThrowExceptionWhenClientIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> WITHDRAW.getTax(null, ZERO))
                .withMessage("client must not be null");
    }

    @Test
    void getTax__shouldThrowExceptionWhenValueIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> WITHDRAW.getTax(mock(Client.class), null))
                .withMessage("value must not be null");
    }
}