package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static com.example.mybank.transaction.TransactionType.WITHDRAW;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;

class TransactionTest {

    @Test
    void constructor__shouldThrowExceptionWhenTypeIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Transaction(null, mock(Client.class), BigDecimal.TEN))
                .withMessage("type must not be null");
    }

    @Test
    void constructor__shouldThrowExceptionWhenClientIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Transaction(WITHDRAW, null, BigDecimal.TEN))
                .withMessage("client must not be null");
    }

    @Test
    void constructor__shouldThrowExceptionWhenValueIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Transaction(WITHDRAW, mock(Client.class), null))
                .withMessage("value must not be null");
    }

    @Test
    void test() {
        Transaction transaction = new Transaction(WITHDRAW, mock(Client.class), BigDecimal.TEN);
        System.out.println(transaction.getAdministrativeTax());
    }

}