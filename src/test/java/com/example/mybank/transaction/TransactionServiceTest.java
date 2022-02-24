package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TransactionServiceTest {

    private TransactionService transactionService;
    private TransactionRepository transactionRepository;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionService = new TransactionService(transactionRepository);
    }

    @Test
    void withdraw__shouldThrowExceptionAndNotSaveTransactionWhenClientIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionService.withdraw(null,  mock(NewWithdrawInputDto.class)))
                .withMessage("client must not be null");
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void withdraw__shouldThrowExceptionAndNotWithdrawAndNotSaveTransactionWhenNewWithdrawInputDtoIsNull() {
        Client client = mock(Client.class);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionService.withdraw(client,  null))
                .withMessage("newWithdrawInputDto must not be null");
        verify(client, never()).withdraw(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void withdraw__shouldCallWithdrawMethodAndSaveTransaction() {
        Client client = mock(Client.class);
        transactionService.withdraw(client, mock(NewWithdrawInputDto.class));
        verify(client).withdraw(any());
        verify(transactionRepository).save(any());
    }

    @Test
    void deposit__shouldThrowExceptionAndNotSaveTransactionWhenClientIsNull() {
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionService.deposit(null,  mock(NewDepositInputDto.class)))
                .withMessage("client must not be null");
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void deposit__shouldThrowExceptionAndNotDepositAndNotSaveTransactionWhenNewDepositInputDtoIsNull() {
        Client client = mock(Client.class);
        assertThatExceptionOfType(IllegalArgumentException.class)
                .isThrownBy(() -> transactionService.deposit(client,  null))
                .withMessage("newDepositInputDto must not be null");
        verify(client, never()).deposit(any());
        verify(transactionRepository, never()).save(any());
    }

    @Test
    void deposit__shouldCallDepositMethodAndSaveTransaction() {
        Client client = mock(Client.class);
        transactionService.deposit(client, mock(NewDepositInputDto.class));
        verify(client).deposit(any());
        verify(transactionRepository).save(any());
    }
}