package com.example.mybank.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Client save(Client client);

    Page<Client> findAll(Pageable pageable);

    Optional<Client> findById(Long id);

    Optional<Client> findByAccountNumber(String accountNumber);
}
