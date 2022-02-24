package com.example.mybank.errors.notFound;

import com.example.mybank.errors.ErrorMessageDto;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ResourceNotFoundController {

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessageDto handleValidationError(ResourceNotFoundException exception) {
        return new ErrorMessageDto("Resource not found");
    }
}
