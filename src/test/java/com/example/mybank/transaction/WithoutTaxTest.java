package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import org.junit.jupiter.api.Test;

import static java.math.BigDecimal.ZERO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

class WithoutTaxTest {

    @Test
    void getValue__shouldAlwaysReturnZero() {
        WithoutTax withoutTax = new WithoutTax();
        assertThat(withoutTax.getValue(null, null)).isEqualTo(ZERO);
        assertThat(withoutTax.getValue(mock(Client.class), null)).isEqualTo(ZERO);
        assertThat(withoutTax.getValue(null, ZERO)).isEqualTo(ZERO);
        assertThat(withoutTax.getValue(mock(Client.class), ZERO)).isEqualTo(ZERO);
    }

}