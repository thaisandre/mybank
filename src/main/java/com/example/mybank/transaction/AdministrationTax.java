package com.example.mybank.transaction;

import com.example.mybank.client.Client;

import java.math.BigDecimal;

import static java.math.BigDecimal.ZERO;
import static java.math.RoundingMode.HALF_UP;
import static org.springframework.util.Assert.notNull;

class AdministrationTax implements Tax {

    private static final BigDecimal INFERIOR_LIMIT = new BigDecimal("100").setScale(4, HALF_UP);
    private static final BigDecimal UPPER_LIMIT = new BigDecimal("300").setScale(4, HALF_UP);
    private static final BigDecimal FOUR_THOUSANDTH = new BigDecimal("0.004").setScale(4, HALF_UP);
    private static final BigDecimal A_TENTH = new BigDecimal("0.1").setScale(4, HALF_UP);

    @Override
    public BigDecimal getValue(Client client, BigDecimal value) {
        notNull(client, "client must not be null");
        notNull(value, "value must not be null");
        if(isClientExclusiveOrValueIsLessThanInferiorLimit(client, value)) return ZERO.setScale(4, HALF_UP);
        else if(isValueGreaterThanInferiorLimitAndLessThanUpperLimit(value)) return FOUR_THOUSANDTH;
        else return A_TENTH;
    }

    private boolean isClientExclusiveOrValueIsLessThanInferiorLimit(Client client, BigDecimal value) {
        return client.isExclusive() || value.compareTo(INFERIOR_LIMIT) <= 0;
    }

    private boolean isValueGreaterThanInferiorLimitAndLessThanUpperLimit(BigDecimal value) {
        return value.compareTo(INFERIOR_LIMIT) > 0 && value.compareTo(UPPER_LIMIT) < 0;
    }
}
