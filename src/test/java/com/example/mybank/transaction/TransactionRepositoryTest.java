package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import com.example.mybank.client.ClientRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.*;
import java.util.List;
import java.util.Optional;

import static com.example.mybank.transaction.TransactionType.DEPOSIT;
import static com.example.mybank.transaction.TransactionType.WITHDRAW;
import static java.math.RoundingMode.HALF_UP;
import static java.time.LocalDate.now;
import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ClientRepository clientRepository;

    private Client client;

    @BeforeEach
    void setUp() {
        tearDown();
        client = new Client("John", "123-9", now().minusYears(18));
        clientRepository.save(client);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void filterByDate__shouldReturnEmptyTransactionsFilterByDateWhenNotExistsInPeriodParameter() {
        Transaction depositTransaction1 = new Transaction(DEPOSIT, client, new BigDecimal("1000.0").setScale(4, HALF_UP));
        Transaction depositTransaction2 = new Transaction(DEPOSIT, client, new BigDecimal("1000.0").setScale(4, HALF_UP));
        Transaction withdrawTransaction = new Transaction(WITHDRAW, client, new BigDecimal("1000.0").setScale(4, HALF_UP));

        LocalDate yesterday = now().minusDays(3);
        transactionRepository.saveAll(List.of(depositTransaction1, depositTransaction2, withdrawTransaction));
        Page<Transaction> transactions = transactionRepository.filterByDate(yesterday, yesterday, PageRequest.of(0, 3));
        assertThat(transactions).isEmpty();
    }

    @Test
    void filterByDate__shouldReturnTransactionsWithPaginationFilterByDateWhenExistsInPeriodParameter() {
        Transaction depositTransaction1 = new Transaction(DEPOSIT, client, new BigDecimal("1000.0").setScale(4, HALF_UP));
        Transaction depositTransaction2 = new Transaction(DEPOSIT, client, new BigDecimal("1000.0").setScale(4, HALF_UP));
        Transaction withdrawTransaction = new Transaction(WITHDRAW, client, new BigDecimal("1000.0").setScale(4, HALF_UP));
        transactionRepository.saveAll(List.of(depositTransaction1, depositTransaction2, withdrawTransaction));

        LocalDate yesterday = now().minusDays(1);
        Page<Transaction> transactions = transactionRepository.filterByDate(yesterday, now(), PageRequest.of(0, 2));
        assertThat(transactions).hasSize(2);

        transactions = transactionRepository.filterByDate(yesterday, now(), PageRequest.of(1, 2));
        assertThat(transactions).hasSize(1);
    }

    @Test
    void filterByDateAndType__shouldReturnTransactionsFilterByDateWhenExistsInPeriodParameterAndType() {
        Transaction depositTransaction1 = new Transaction(DEPOSIT, client, new BigDecimal("1000.0").setScale(4, HALF_UP));
        Transaction depositTransaction2 = new Transaction(DEPOSIT, client, new BigDecimal("1000.0").setScale(4, HALF_UP));
        Transaction withdrawTransaction = new Transaction(WITHDRAW, client, new BigDecimal("1000.0").setScale(4, HALF_UP));
        transactionRepository.saveAll(List.of(depositTransaction1, depositTransaction2, withdrawTransaction));

        LocalDate yesterday = now().minusDays(1);

        Page<Transaction> transactions = transactionRepository.filterByDateAndType(yesterday, now(), PageRequest.of(0, 2), Optional.of(WITHDRAW));
        assertThat(transactions).hasSize(1);
        assertThat(transactions).containsExactlyInAnyOrder(withdrawTransaction);

        transactions = transactionRepository.filterByDateAndType(yesterday, now(), PageRequest.of(0, 3), Optional.of(DEPOSIT));
        assertThat(transactions).hasSize(2);
        assertThat(transactions).containsExactlyInAnyOrder(depositTransaction1, depositTransaction2);
    }
}