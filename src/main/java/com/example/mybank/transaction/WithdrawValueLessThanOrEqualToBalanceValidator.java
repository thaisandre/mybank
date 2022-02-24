package com.example.mybank.transaction;

import com.example.mybank.client.Client;
import com.example.mybank.client.ClientRepository;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.util.Optional;

@Component
class WithdrawValueLessThanOrEqualToBalanceValidator implements Validator {

    private final ClientRepository clientRepository;

    public WithdrawValueLessThanOrEqualToBalanceValidator(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return NewWithdrawInputDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        NewWithdrawInputDto newWithdrawInputDto = (NewWithdrawInputDto) target;
        Optional<Client> possibleClient = clientRepository.findByAccountNumber(newWithdrawInputDto.getAccountNumber());

        if(possibleClient.isPresent() && isBalanceInsufficient(possibleClient.get().getBalance(), newWithdrawInputDto.getValue())) {
            errors.reject("newWithdrawInputDto.insufficient.funds", "Insufficient funds");
        }
    }

    private boolean isBalanceInsufficient(BigDecimal balance, BigDecimal value) {
        return balance.compareTo(value) <= 0;
    }
}
