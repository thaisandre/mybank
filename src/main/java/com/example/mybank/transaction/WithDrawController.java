package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import com.example.mybank.client.ClientRepository;
import com.example.mybank.errors.notFound.IfResourceIsFound;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class WithDrawController {

    private final ClientRepository clientRepository;
    private final TransactionService transactionService;
    private final WithdrawValueLessThanOrEqualToBalanceValidator withdrawValueLessThanOrEqualToBalanceValidator;

    public WithDrawController(ClientRepository clientRepository, TransactionService transactionService, WithdrawValueLessThanOrEqualToBalanceValidator withdrawValueLessThanOrEqualToBalanceValidator) {
        this.clientRepository = clientRepository;
        this.transactionService = transactionService;
        this.withdrawValueLessThanOrEqualToBalanceValidator = withdrawValueLessThanOrEqualToBalanceValidator;
    }

    @InitBinder("newWithdrawInputDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(withdrawValueLessThanOrEqualToBalanceValidator);
    }

    @PostMapping("/api/v1/withdraw")
    public ResponseEntity withdraw(@Valid @RequestBody NewWithdrawInputDto newWithdrawInputDto, UriComponentsBuilder uriBuilder) {
        Client client = IfResourceIsFound.of(clientRepository.findByAccountNumber(newWithdrawInputDto.getAccountNumber()));
        Transaction transaction = transactionService.withdraw(client, newWithdrawInputDto);
        URI path = uriBuilder.path("/api/v1/transactions/{id}").buildAndExpand(transaction.getId()).toUri();
        return ResponseEntity.created(path).body(new TransactionOutputDto(transaction));
    }
}
