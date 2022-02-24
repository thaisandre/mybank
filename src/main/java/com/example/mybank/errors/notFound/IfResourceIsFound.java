package com.example.mybank.errors.notFound;

import java.util.Optional;

import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notNull;

public class IfResourceIsFound {

    private static final String RESOURCE_NOT_FOUND = "Resource not found";

    public static <T> T of(Optional<T> optional) {
        return of(optional, RESOURCE_NOT_FOUND);
    }

    public static <T> T of(Optional<T> optional, String message) {
        notNull(optional, "optional must not be null");
        hasText(message, "message must not be blank");
        return optional.orElseThrow(() -> new ResourceNotFoundException(message));
    }
}
