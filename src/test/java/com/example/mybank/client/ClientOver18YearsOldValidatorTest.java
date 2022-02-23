package com.example.mybank.client;

import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import java.time.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ClientOver18YearsOldValidatorTest {

    private ClientOver18YearsOldValidator validator = new ClientOver18YearsOldValidator();

    @Test
    void supports__shouldReturnTrueWhenClassIsNewClientInputDto() {
        assertThat(validator.supports(NewClientInputDto.class)).isTrue();
    }

    @Test
    void supports__shouldReturnFalseWhenClassIsNotNewClientInputDto() {
        assertThat(validator.supports(String.class)).isFalse();
        assertThat(validator.supports(Integer.class)).isFalse();
        assertThat(validator.supports(Client.class)).isFalse();
    }

    @Test
    void validate__shouldNotAddErrorWhenClientIsOver18YearsOld() {
        LocalDate eighteenFromNow = LocalDate.now().minusYears(18);

        NewClientInputDto newClientInputDto = mock(NewClientInputDto.class);
        when(newClientInputDto.getBirthDate()).thenReturn(eighteenFromNow);

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(newClientInputDto, "newClientInputDto");
        validator.validate(newClientInputDto, result);

        assertThat(result.getFieldErrors()).isEmpty();
    }

    @Test
    void validate__shouldAddErrorWhenClientIsUnder18YearsOls() {
        LocalDate seventeen = LocalDate.now().minusYears(17);

        NewClientInputDto newClientInputDto = mock(NewClientInputDto.class);
        when(newClientInputDto.getBirthDate()).thenReturn(seventeen);

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(newClientInputDto, "newClientInputDto");
        validator.validate(newClientInputDto, result);

        assertThat(result.getFieldErrors()).isNotEmpty();
        assertThat(result.getFieldErrors().get(0).getField()).isEqualTo("birthDate");
        assertThat(result.getFieldErrors().get(0).getDefaultMessage()).isEqualTo("The client must be over 18 years old");
    }
}