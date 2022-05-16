package ru.sberbank.local.component.bankAccountComponent.bankAccountBuilder;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.stream.Collectors;

/**
 * Компонент создает нужные для счета номера
 */
@Component
public class BankAccountNumbersFieldBuilderComponent {

    private final int BIK_POSTFIX_MAX_LENGTH = 5;
    private final int INN_POSTFIX_MAX_LENGTH = 6;
    private final int ACCOUNT_BENEFICIARY_CODE_POSTFIX_MAX_LENGTH = 17;
    private final String BIK_COUNTY_OKATO_CODE_PREFIX = "0436";
    private final String INN_FNS_CODE_PREFIX = "7707";
    private final String ACCOUNT_BENEFICIARY_CODE_PREFIX = "408";
    private final StringBuffer codeBuilder = new StringBuffer();

    /**
     * Метод генерирует бик путем складывания кода страны и кода ОКАТО,
     * а так же BIK_POSTFIX_MAX_LENGTH колличества рандомных чисел
     *
     * @return БИК
     */
    protected String generateBik() {
        codeBuilder.delete(0, Integer.MAX_VALUE);
        codeBuilder
                .append(BIK_COUNTY_OKATO_CODE_PREFIX)
                .append(new SecureRandom()
                        .ints(BIK_POSTFIX_MAX_LENGTH, 0, 9)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining()));
        return codeBuilder.toString();
    }

    /**
     * Метод генерирует номер счета путем складывания префикса физ лица,
     * а так же ACCOUNT_BENEFICIARY_CODE_POSTFIX_MAX_LENGTH колличества рандомных чисел
     *
     * @return Номер счета
     */
    protected String generateBeneficiaryAccount() {
        codeBuilder.delete(0, Integer.MAX_VALUE);
        codeBuilder
                .append(ACCOUNT_BENEFICIARY_CODE_PREFIX)
                .append(new SecureRandom()
                        .ints(ACCOUNT_BENEFICIARY_CODE_POSTFIX_MAX_LENGTH, 0, 9)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining()));
        return codeBuilder.toString();
    }

    /**
     * Метод генерирует ИНН путем складывания префикса кода подразделения ФНС,
     * а так же INN_POSTFIX_MAX_LENGTH колличества рандомных чисел
     *
     * @return Номер счета
     */
    protected String generateInn() {
        codeBuilder.delete(0, Integer.MAX_VALUE);
        codeBuilder
                .append(INN_FNS_CODE_PREFIX)
                .append(new SecureRandom()
                        .ints(INN_POSTFIX_MAX_LENGTH, 0, 9)
                        .mapToObj(String::valueOf)
                        .collect(Collectors.joining()));
        return codeBuilder.toString();
    }


}
