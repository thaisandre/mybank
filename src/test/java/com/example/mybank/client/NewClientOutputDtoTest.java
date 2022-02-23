package com.example.mybank.client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class NewClientOutputDtoTest {

    @Test
    void constructor__shouldThrowExceptionWhenClientIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new NewClientOutputDto(null))
                .withMessage("client must not be null");
    }
}