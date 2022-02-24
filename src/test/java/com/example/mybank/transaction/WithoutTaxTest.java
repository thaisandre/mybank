package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class WithoutTaxTest {

    @Test
    void getValue__shouldAlwaysReturnZero() {
        WithoutTax withoutTax = new WithoutTax();
        BigDecimal zero = ZERO.setScale(4, RoundingMode.HALF_UP);
        assertThat(withoutTax.getValue(null, null)).isEqualTo(zero);
        assertThat(withoutTax.getValue(mock(Client.class), null)).isEqualTo(zero);
        assertThat(withoutTax.getValue(null, ZERO)).isEqualTo(zero);
        assertThat(withoutTax.getValue(mock(Client.class), ZERO)).isEqualTo(zero);
    }

}