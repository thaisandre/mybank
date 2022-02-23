package com.example.mybank.client;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Optional;

@Component
class AccountNumberAlreadyExistsValidator implements Validator {

    private final ClientRepository clientRepository;

    public AccountNumberAlreadyExistsValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewClientInputDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewClientInputDto newClientInputDto = (NewClientInputDto) target;
        String accountNumber = newClientInputDto.getAccountNumber();

        Optional<Client> possibleClient = clientRepository.findByAccountNumber(accountNumber);
        if(possibleClient.isPresent()) {
            errors.rejectValue("accountNumber", "newClientInputDto.accountNumber.already.exists",
                    new Object[]{accountNumber},
                    "There is already a client with this account number: " + accountNumber);
        }
    }
}
