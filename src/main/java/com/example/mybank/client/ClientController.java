package com.example.mybank.client;

import com.example.mybank.errors.notFound.IfResourceIsFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class ClientController {

    @Autowired private ClientRepository clientRepository;
    @Autowired private AccountNumberAlreadyExistsValidator accountNumberAlreadyExistsValidator;
    @Autowired private ClientOver18YearsOldValidator clientOver18YearsOldValidator;

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(accountNumberAlreadyExistsValidator, clientOver18YearsOldValidator);
    }

    @GetMapping("/api/v1/clients")
    public ResponseEntity list(Pageable pageable) {
        List<ClientOutputDto> clients = clientRepository.findAll(pageable).getContent().stream().map(ClientOutputDto::new).toList();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/api/v1/clients/{id}")
    public ResponseEntity get(@PathVariable("id") Long id) {
        Client client = IfResourceIsFound.of(clientRepository.findById(id));
        return ResponseEntity.ok(new ClientOutputDto(client));
    }

    @PostMapping("/api/v1/clients")
    public ResponseEntity create(@Valid @RequestBody NewClientInputDto newClientDto, UriComponentsBuilder uriBuilder) {
        Client client = clientRepository.save(newClientDto.toEntity());
        URI path = uriBuilder.path("/clients/{id}").buildAndExpand(client.getId()).toUri();
        return ResponseEntity.created(path).body(new NewClientOutputDto(client));
    }

}
