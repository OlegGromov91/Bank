package com.data.model.bank.card;

import java.math.BigDecimal;

public enum CurrencyType {

    RUR(9, new BigDecimal("0")),
    EUR(8, new BigDecimal("90")),
    USD(7, new BigDecimal("80"));

    public final int currencyCode;
    public final BigDecimal currencyCurse;

    CurrencyType(int currencyCode, BigDecimal currencyCurse) {
        this.currencyCode = currencyCode;
        this.currencyCurse = currencyCurse;
    }
}
