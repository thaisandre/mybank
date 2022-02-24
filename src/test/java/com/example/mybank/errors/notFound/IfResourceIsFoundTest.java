package com.example.mybank.errors.notFound;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class IfResourceIsFoundTest {

    @Test
    void of__shouldThrowExceptionWhenOptionalIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> IfResourceIsFound.of(null))
                .withMessage("optional must not be null");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> IfResourceIsFound.of(null, "content"))
                .withMessage("optional must not be null");
    }

    @Test
    void of__shouldThrowExceptionWhenMessageIsBlank() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> IfResourceIsFound.of(Optional.empty(), null))
                .withMessage("message must not be blank");

        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> IfResourceIsFound.of(Optional.empty(), ""))
                .withMessage("message must not be blank");
    }
}