package com.example.mybank.errors.badRequest;

import java.util.ArrayList;
import java.util.List;

class ValidationErrorsOutputDto {

    private List<String> globalErrorMessages = new ArrayList<>();
    private List<FieldErrorOutputDto> fieldErrors = new ArrayList<>();

    public void addError(String message) {
        globalErrorMessages.add(message);
    }

    public void addFieldError(String field, String message) {
        FieldErrorOutputDto fieldError = new FieldErrorOutputDto(field, message);
        fieldErrors.add(fieldError);
    }

    public List<String> getGlobalErrorMessages() {
        return globalErrorMessages;
    }

    public List<FieldErrorOutputDto> getFieldErrors() {
        return fieldErrors;
    }

    public int getNumberOfErrors() {
        return this.fieldErrors.size() + this.globalErrorMessages.size();
    }

}
