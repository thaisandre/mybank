package com.example.mybank.client;

import com.example.mybank.errors.notFound.IfResourceIsFound;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class ClientController {

    private final ClientRepository clientRepository;
    private final AccountNumberAlreadyExistsValidator accountNumberAlreadyExistsValidator;
    private final ClientOver18YearsOldValidator clientOver18YearsOldValidator;

    public ClientController(ClientRepository clientRepository, AccountNumberAlreadyExistsValidator accountNumberAlreadyExistsValidator, ClientOver18YearsOldValidator clientOver18YearsOldValidator) {
        this.clientRepository = clientRepository;
        this.accountNumberAlreadyExistsValidator = accountNumberAlreadyExistsValidator;
        this.clientOver18YearsOldValidator = clientOver18YearsOldValidator;
    }

    @InitBinder("newClientInputDto")
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountNumberAlreadyExistsValidator, clientOver18YearsOldValidator);
    }

    @GetMapping("/api/v1/clients")
    public ResponseEntity list(Pageable pageable) {
        Page<ClientOutputDto> clients = clientRepository.findAll(pageable).map(ClientOutputDto::new);
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/api/v1/clients/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Client client = IfResourceIsFound.of(clientRepository.findById(id));
        return ResponseEntity.ok(new ClientOutputDto(client));
    }

    @PostMapping("/api/v1/clients")
    public ResponseEntity create(@Valid @RequestBody NewClientInputDto newClientInputDto, UriComponentsBuilder uriBuilder) {
        Client client = clientRepository.save(newClientInputDto.toEntity());
        URI path = uriBuilder.path("/clients/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(path).body(new NewClientOutputDto(client));
    }

}
