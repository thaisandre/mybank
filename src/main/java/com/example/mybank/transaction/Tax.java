package com.example.mybank.transaction;

import com.example.mybank.client.Client;

import java.math.BigDecimal;

interface Tax {

    BigDecimal getValue(Client client, BigDecimal valor);
}
