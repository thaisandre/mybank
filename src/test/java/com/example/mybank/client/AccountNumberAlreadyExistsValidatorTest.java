package com.example.mybank.client;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import java.util.Optional;

import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AccountNumberAlreadyExistsValidatorTest {

    private AccountNumberAlreadyExistsValidator validator;
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        validator = new AccountNumberAlreadyExistsValidator(clientRepository);
    }

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
    void validate__shouldNotAddErrorWhenAccountNumberNotExists() {
        NewClientInputDto newClientInputDto = mock(NewClientInputDto.class);
        when(newClientInputDto.getAccountNumber()).thenReturn("123-4");
        when(clientRepository.findByAccountNumber("123-4")).thenReturn(empty());

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(newClientInputDto, "newClientInputDto");
        validator.validate(newClientInputDto, result);

        assertThat(result.getFieldErrors()).isEmpty();
    }

    @Test
    void validate__shouldAddErrorWhenAccountNumberAlreadyExists() {
        NewClientInputDto newClientInputDto = mock(NewClientInputDto.class);
        when(newClientInputDto.getAccountNumber()).thenReturn("123-4");
        when(clientRepository.findByAccountNumber("123-4")).thenReturn(Optional.of(mock(Client.class)));

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(newClientInputDto, "newClientInputDto");
        validator.validate(newClientInputDto, result);

        assertThat(result.getAllErrors()).isNotEmpty();
        assertThat(result.getFieldErrors()).isNotEmpty();
        assertThat(result.getFieldErrors().get(0).getField()).isEqualTo("accountNumber");
        assertThat(result.getFieldErrors().get(0).getDefaultMessage()).isEqualTo("There is already a client with this account number: " + newClientInputDto.getAccountNumber());
    }
}