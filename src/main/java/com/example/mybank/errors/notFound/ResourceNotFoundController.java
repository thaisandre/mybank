package com.example.mybank.errors.notFound;

import com.example.mybank.errors.ErrorMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice
public class ResourceNotFoundController {

    private final static Logger log = LoggerFactory.getLogger(ResourceNotFoundController.class);

    @ResponseStatus(NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorMessageDto handleValidationError(ResourceNotFoundException exception) {
        log.warn("[mybank.ResourceNotFoundController] Resource not found: " + exception.getMessage());
        return new ErrorMessageDto("Resource not found");
    }
}
