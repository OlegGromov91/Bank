package com.validation;

import com.validation.exceptions.IllegalOperationException;

import java.math.BigDecimal;
import java.util.Objects;

public interface Validator {

    int MAX_CVV_CODE_LENGTH = 3;
    int MAX_PIN_CODE_LENGTH = 4;
    int MAX_INN_CODE_LENGTH = 10;
    int MAX_BIK_CODE_LENGTH = 9;
    int MAX_BENEFICIARY_ACCOUNT_CODE_LENGTH = 20;


    /**
     * Базовая проверка кода сущности на пустоту, налл и необходимую длинну
     *
     * @param code кодовое поле объекта
     * @return валидность
     */
    default boolean codeIsValid(String code, int length) {
        return Objects.nonNull(code) & !code.isBlank() & !code.isEmpty() & code.length() == length;
    }

    /**
     * Метод проверяет достаточно ли денег для транзакций внутри банка.
     *
     * @param inputMoney сумма для перевода
     * @param userMoney  сумма у клиента
     * @return достаточно ли денег
     */
    default boolean isMoneyEnough(String inputMoney, BigDecimal userMoney) {

        BigDecimal input = new BigDecimal(inputMoney);
        if (input.compareTo(userMoney) <= 0) {
            return true;
        }
        throw new IllegalOperationException("user.has.no.enough.money");
    }
}
