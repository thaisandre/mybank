package com.example.mybank.client;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class ClientOutputDtoTest {

    @Test
    void constructor__shouldThrowExceptionWhenClientIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> new ClientOutputDto(null))
                .withMessage("client must not be null");
    }
}