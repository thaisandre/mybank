package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import com.example.mybank.client.ClientRepository;
import com.example.mybank.errors.notFound.IfResourceIsFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class DepositController {

    private final ClientRepository clientRepository;
    private final TransactionService transactionService;

    public DepositController(ClientRepository clientRepository, TransactionService transactionService) {
        this.clientRepository = clientRepository;
        this.transactionService = transactionService;
    }

    @PostMapping("/api/v1/deposit")
    public ResponseEntity deposit(@Valid @RequestBody NewDepositInputDto newDepositInputDto, UriComponentsBuilder uriBuilder) {
        Client client = IfResourceIsFound.of(clientRepository.findByAccountNumber(newDepositInputDto.getAccountNumber()));
        Transaction transaction = transactionService.deposit(client, newDepositInputDto);
        URI path = uriBuilder.path("/api/v1/transactions/{id}").buildAndExpand(transaction.getId()).toUri();
        return ResponseEntity.created(path).body(new TransactionOutputDto(transaction));
    }

}
