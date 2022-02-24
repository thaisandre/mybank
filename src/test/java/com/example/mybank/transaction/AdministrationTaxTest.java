package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdministrationTaxTest {

    private static final BigDecimal INFERIOR_LIMIT = new BigDecimal("100").setScale(4, HALF_UP);
    private static final BigDecimal UPPER_LIMIT = new BigDecimal("300").setScale(4, HALF_UP);
    private static final BigDecimal FOUR_THOUSANDTH = new BigDecimal("0.004").setScale(4, HALF_UP);
    private static final BigDecimal A_TENTH = new BigDecimal("0.1").setScale(4, HALF_UP);

    private AdministrationTax administrationTax;

    @BeforeEach
    void setUp() {
        administrationTax = new AdministrationTax();
    }

    @Test
    void getValue__shouldThrowExceptionWhenClientIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> administrationTax.getValue(null, TEN))
                .withMessage("client must not be null");
    }

    @Test
    void getValue__shouldThrowExceptionWhenValueIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> administrationTax.getValue(mock(Client.class), null))
                .withMessage("value must not be null");
    }

    @Test
    void getValue__shouldReturnZeroWhenClientIsExclusive() {
        Client client = mock(Client.class);
        when(client.isExclusive()).thenReturn(true);
        assertThat(administrationTax.getValue(client, UPPER_LIMIT)).isEqualTo(ZERO.setScale(4, HALF_UP));
        assertThat(administrationTax.getValue(client, INFERIOR_LIMIT)).isEqualTo(ZERO.setScale(4, HALF_UP));
    }

    @ParameterizedTest
    @CsvSource({"0.01", "50", "99.99", "100"})
    void getValue__shouldReturnZeroWhenValueIsLessThanOrEqualInferiorLimit(String value) {
        Client client = mock(Client.class);
        BigDecimal taxValue = administrationTax.getValue(client, new BigDecimal(value).setScale(4, HALF_UP));
        assertThat(taxValue).isEqualTo(ZERO.setScale(4, HALF_UP));
    }

    @ParameterizedTest
    @CsvSource({"100.01", "150", "200", "250", "299.99"})
    void getValue__shouldReturnFourThousandthPercentWhenValueIsGreaterThanInferiorLimitAndLessThanUpperLimit(String value) {
        Client client = mock(Client.class);
        BigDecimal taxValue = administrationTax.getValue(client, new BigDecimal(value).setScale(4, HALF_UP));
        assertThat(taxValue).isEqualTo(FOUR_THOUSANDTH);
    }

    @ParameterizedTest
    @CsvSource({"300.01", "350", "400", "450", "500"})
    void getValue__shouldReturnOneTenthsPercentWhenValueIsGreaterThanUpperLimit(String value) {
        Client client = mock(Client.class);
        BigDecimal taxValue = administrationTax.getValue(client, new BigDecimal(value).setScale(4, HALF_UP));
        assertThat(taxValue).isEqualTo(A_TENTH);
    }
}