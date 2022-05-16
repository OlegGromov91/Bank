package ru.sberbank.local.component.cardComponent;

import java.math.BigDecimal;

/**
 *  Список банков со значениями в виде коммиссии
 */
public enum BanksCommission {

    ALPHA_BANK(new BigDecimal("30")),
    TINKOFF_BANK(new BigDecimal("25"));

    public final BigDecimal commissionAmount;

    BanksCommission(BigDecimal commissionAmount) {
        this.commissionAmount = commissionAmount;
    }
}
