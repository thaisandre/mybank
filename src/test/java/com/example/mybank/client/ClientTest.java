package com.example.mybank.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.math.RoundingMode.HALF_UP;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

class ClientTest {

    private Client validClient;
    private static final LocalDate EIGHTEEN_YEARS_AGO = now().minusYears(18);

    @BeforeEach
    public void setUp() {
        this.validClient = spy(new Client("John", "123-4", EIGHTEEN_YEARS_AGO));
    }

    @Test
    void constructor__shouldThrowExceptionWhenNameIsBlank() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Client(null, "123-4", EIGHTEEN_YEARS_AGO))
                .withMessage("name must not be null or empty");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Client("", "123-4", EIGHTEEN_YEARS_AGO))
                .withMessage("name must not be null or empty");
    }

    @Test
    void constructor__shouldThrowExceptionWhenAccountNumberIsBlank() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Client("John", null, EIGHTEEN_YEARS_AGO))
                .withMessage("accountNumber must not be null or empty");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Client("John", "", EIGHTEEN_YEARS_AGO))
                .withMessage("accountNumber must not be null or empty");
    }

    @Test
    void constructor__shouldThrowExceptionWhenBirthDateIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Client("John", "123-4", null))
                .withMessage("birthDate must not be null");
    }

    @Test
    void constructor__shouldThrowExceptionWhenClientIsLessThan18YearsAgo() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new Client("John", "123-4", EIGHTEEN_YEARS_AGO.plusYears(1)))
                .withMessage("The client must be over 18 years old");
    }

    @Test
    void withdraw__shouldThrowExceptionWhenValueIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validClient.withdraw(null))
                .withMessage("value must not be null");
    }

    @Test
    void withdraw__shouldThrowExceptionWhenValueIsNotPositive() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validClient.withdraw(BigDecimal.ZERO))
                .withMessage("value must be a positive number");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validClient.withdraw(new BigDecimal("-1")))
                .withMessage("value must be a positive number");
    }

    @Test
    void withdraw__shouldThrowExceptionWhenValueIsGreaterThanBalance() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validClient.withdraw(BigDecimal.valueOf(100)))
                .withMessage("value must not be greater than balance");
    }

    @ParameterizedTest
    @CsvSource({"1000,0.99,999.01", "1000,1,999", "1000,50,950", "1000,99.99,900.01", "1000,100,900"})
    void withdraw__shouldUpdateBalanceWithoutTaxWhenClientIsLessThanOneHundred(String balance, String value, String updatedBalance) {
        BigDecimal oneHundred = new BigDecimal(balance).setScale(4, HALF_UP);
        validClient.deposit(oneHundred);

        BigDecimal withdrawValue = new BigDecimal(value).setScale(4, HALF_UP);
        BigDecimal expectedBalance = new BigDecimal(updatedBalance).setScale(4, HALF_UP);

        validClient.withdraw(withdrawValue);
        assertThat(validClient.getBalance()).isEqualTo(expectedBalance);
    }

    @ParameterizedTest
    @CsvSource({"1000,0.99,999.01", "1000,100,900", "1000,101,899", "1000,299.99,700.01", "1000,300,700", "1000,301,699"})
    void withdraw__shouldUpdateBalanceWithoutTaxWhenClientIsExclusive(String balance, String value, String updatedBalance) {
        BigDecimal oneHundred = new BigDecimal(balance).setScale(4, HALF_UP);
        validClient.deposit(oneHundred);
        when(validClient.isExclusive()).thenReturn(true);

        BigDecimal withdrawValue = new BigDecimal(value).setScale(4, HALF_UP);
        BigDecimal expectedBalance = new BigDecimal(updatedBalance).setScale(4, HALF_UP);

        validClient.withdraw(withdrawValue);
        assertThat(validClient.getBalance()).isEqualTo(expectedBalance);
    }

    @ParameterizedTest
    @CsvSource({"1000,101,898.596", "1000,200,799.20", "1000,299.99,698.81"})
    void withdraw__shouldUpdateBalanceWithFourTenthsPercentTaxWhenValueIsGreaterThanOneHundredOrLessThanThreeHundred(String balance, String value, String updatedBalance) {
        BigDecimal depositValue = new BigDecimal(balance).setScale(4, HALF_UP);
        validClient.deposit(depositValue);

        BigDecimal withdrawValue = new BigDecimal(value).setScale(4, HALF_UP);
        BigDecimal expectedBalance = new BigDecimal(updatedBalance).setScale(4, HALF_UP);

        validClient.withdraw(withdrawValue);
        assertThat(validClient.getBalance()).isEqualTo(expectedBalance);
    }

    @ParameterizedTest
    @CsvSource({"1000,300.01,669.989", "1000,301,668.9", "1000,500,450"})
    void withdraw__shouldUpdateBalanceOnePercentTaxWhenValueIsGreaterThanThreeHundred(String balance, String value, String updatedBalance) {
        BigDecimal depositValue = new BigDecimal(balance).setScale(4, HALF_UP);
        validClient.deposit(depositValue);

        BigDecimal withdrawValue = new BigDecimal(value).setScale(4, HALF_UP);
        BigDecimal expectedBalance = new BigDecimal(updatedBalance).setScale(4, HALF_UP);

        validClient.withdraw(withdrawValue);
        assertThat(validClient.getBalance()).isEqualTo(expectedBalance);
    }

    @Test
    void deposit__shouldThrowExceptionWhenValueIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validClient.deposit(null))
                .withMessage("value must not be null");
    }

    @Test
    void deposit__shouldThrowExceptionWhenValueIsNotPositive() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validClient.deposit(BigDecimal.ZERO))
                .withMessage("value must be a positive number");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> validClient.deposit(new BigDecimal("-1")))
                .withMessage("value must be a positive number");
    }

    @ParameterizedTest
    @CsvSource({"0.01,0.01", "299.667,299.667", "543,543"})
    void deposit__shouldAddValueToBalanceWhenValueIsPositive(String value, String updatedBalance) {
        assertThat(validClient.getBalance()).isEqualTo(BigDecimal.ZERO.setScale(4, HALF_UP));
        BigDecimal depositValue = new BigDecimal(value).setScale(4, HALF_UP);
        BigDecimal expectedBalance = new BigDecimal(updatedBalance).setScale(4, HALF_UP);
        validClient.deposit(depositValue);
        assertThat(validClient.getBalance()).isEqualTo(expectedBalance);
    }
}