package ru.sberbank.local.component.cardComponent.validation;

import ru.sberbank.local.comons.exceptions.IllegalOperationException;

import java.math.BigDecimal;

/**
 * Основаная валидация для операций по карте
 */
public abstract class BaseCardValidator implements CardValidator {



    public boolean isMoneyEnoughWithCommission(String inputMoney, BigDecimal moneyOnCard, BigDecimal commission) {
        BigDecimal sumWithCommission = new BigDecimal(inputMoney).add(commission);
        return isMoneyEnough(sumWithCommission.toString(), moneyOnCard);
    }


}
