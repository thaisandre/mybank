package com.example.mybank.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import static java.time.LocalDate.now;
import static java.time.temporal.ChronoUnit.YEARS;

@Component
class ClientOver18YearsOldValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewClientInputDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewClientInputDto newClientInputDto = (NewClientInputDto) target;
        LocalDate birthDate = newClientInputDto.getBirthDate();

        if(getClientAge(birthDate) < 18) {
            errors.rejectValue("birthDate", "newClientInputDto.birthDate.underage",
                    "The client must be over 18 years old");
        }
    }

    private int getClientAge(LocalDate birthDate) {
        return (int) YEARS.between(birthDate, now());
    }
}
