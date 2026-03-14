package com.samir.pfm.domain.transaction.model;

import java.math.BigDecimal;
import java.util.Objects;

public record Money(BigDecimal amount, String currency) {
    public Money {
        Objects.requireNonNull(amount);
        Objects.requireNonNull(currency);
    }

    public static Money dzd(BigDecimal amount) {
        return new Money(amount, "DZD");
    }
}
