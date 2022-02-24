package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import com.example.mybank.client.ClientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class WithdrawValueLessThanOrEqualToBalanceValidatorTest {

    private WithdrawValueLessThanOrEqualToBalanceValidator validator;
    private ClientRepository clientRepository;

    @BeforeEach
    void setUp() {
        clientRepository = mock(ClientRepository.class);
        validator = new WithdrawValueLessThanOrEqualToBalanceValidator(clientRepository);
    }

    @Test
    void supports__shouldReturnTrueWhenClassIsNewClientInputDto() {
        assertThat(validator.supports(NewWithdrawInputDto.class)).isTrue();
    }

    @Test
    void supports__shouldReturnFalseWhenClassIsNotNewClientInputDto() {
        assertThat(validator.supports(String.class)).isFalse();
        assertThat(validator.supports(Integer.class)).isFalse();
        assertThat(validator.supports(Client.class)).isFalse();
    }

    @Test
    void validate__shouldNotAddErrorWhenClientAccountNumberNotExists() {
        NewWithdrawInputDto newWithdrawInputDto = mock(NewWithdrawInputDto.class);
        when(clientRepository.findByAccountNumber(newWithdrawInputDto.getAccountNumber())).thenReturn(Optional.empty());

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(newWithdrawInputDto, "newWithdrawInputDto");
        validator.validate(newWithdrawInputDto, result);

        assertThat(result.getAllErrors()).isEmpty();
    }

    @Test
    void validate__shouldNotAddErrorWhenClientAccountNumberExistsAndBalanceIsSufficient() {
        NewWithdrawInputDto newWithdrawInputDto = mock(NewWithdrawInputDto.class);
        when(newWithdrawInputDto.getValue()).thenReturn(BigDecimal.ONE);

        Client client = mock(Client.class);
        when(client.getBalance()).thenReturn(BigDecimal.TEN);
        when(clientRepository.findByAccountNumber(newWithdrawInputDto.getAccountNumber())).thenReturn(Optional.of(client));

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(newWithdrawInputDto, "newWithdrawInputDto");
        validator.validate(newWithdrawInputDto, result);

        assertThat(result.getAllErrors()).isEmpty();
    }

    @Test
    void validate__shouldAddErrorWhenClientAccountNumberExistsAndBalanceIsInsufficient() {
        NewWithdrawInputDto newWithdrawInputDto = mock(NewWithdrawInputDto.class);
        when(newWithdrawInputDto.getValue()).thenReturn(BigDecimal.TEN);

        Client client = mock(Client.class);
        when(client.getBalance()).thenReturn(BigDecimal.TEN);
        when(clientRepository.findByAccountNumber(newWithdrawInputDto.getAccountNumber())).thenReturn(Optional.of(client));

        BeanPropertyBindingResult result = new BeanPropertyBindingResult(newWithdrawInputDto, "newWithdrawInputDto");
        validator.validate(newWithdrawInputDto, result);

        assertThat(result.getAllErrors()).isNotEmpty();
        assertThat(result.getAllErrors().get(0).getDefaultMessage()).isEqualTo("Insufficient funds");
    }
}