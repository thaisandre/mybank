package com.example.mybank.transaction;

import com.example.mybank.client.Client;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;

class WithoutTax implements Tax {

    @Override
    public BigDecimal getValue(Client client, BigDecimal valor) {
        return ZERO;
    }
}
