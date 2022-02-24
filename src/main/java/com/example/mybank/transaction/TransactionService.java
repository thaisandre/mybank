package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.util.Assert.notNull;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Transactional
    public Transaction withdraw(Client client, NewWithdrawInputDto newWithdrawInputDto) {
        notNull(client, "client must not be null");
        notNull(newWithdrawInputDto, "newWithdrawInputDto must not be null");
        client.withdraw(newWithdrawInputDto.getValue());
        return transactionRepository.save(newWithdrawInputDto.toEntity(client));
    }

    @Transactional
    public Transaction deposit(Client client, NewDepositInputDto newDepositInputDto) {
        notNull(client, "client must not be null");
        notNull(newDepositInputDto, "newDepositInputDto must not be null");
        client.deposit(newDepositInputDto.getValue());
        return transactionRepository.save(newDepositInputDto.toEntity(client));
    }
}
