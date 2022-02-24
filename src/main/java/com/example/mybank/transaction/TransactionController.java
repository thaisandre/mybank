package com.example.mybank.transaction;

import com.example.mybank.errors.notFound.IfResourceIsFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Optional.ofNullable;

@RestController
public class TransactionController {

    private final TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/api/v1/transactions")
    public ResponseEntity list(Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.findAll(pageable);
        return ResponseEntity.ok(transactions.map(TransactionOutputDto::new));
    }

    @GetMapping("/api/v1/transactions/{id}")
    public ResponseEntity list(@PathVariable("id") Long id) {
        Transaction transaction = IfResourceIsFound.of(transactionRepository.findById(id));
        return ResponseEntity.ok(new TransactionOutputDto(transaction));
    }

    @GetMapping("/api/v1/transactions/filter")
    public ResponseEntity filterByDateAndType(TransactionFilterDto filter, Pageable pageable) {
        Page<Transaction> transactions = transactionRepository.filterByDateAndType(filter.getFromDate(), filter.getToDate(), pageable, ofNullable(filter.getType()));
        return ResponseEntity.ok(transactions.map(TransactionOutputDto::new));
    }
}
