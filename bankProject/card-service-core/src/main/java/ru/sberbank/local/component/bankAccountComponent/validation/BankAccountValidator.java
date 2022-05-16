package ru.sberbank.local.component.bankAccountComponent.validation;

import java.time.LocalDate;

/**
 * Валидатор банковского счета
 */
public interface BankAccountValidator {

    /**
     *Метод проверяет правильность введенного банковского счета
     * @param beneficiaryAccount номер банковского счета
     * @param inputBeneficiaryAccount входной номер банковского счета
     * @return валидность
     */
    boolean isBeneficiaryAccountValid(String inputBeneficiaryAccount, String beneficiaryAccount);

    /**
     *Метод проверяет правильность введенного бик
     * @param bik бик
     * @param inputBik входной бик
     * @return валидность
     */
    boolean isBikValid(String inputBik, String bik);

    /**
     *Метод проверяет правильность введенного инн
     * @param inn инн
     * @param inputInn входной инн
     * @return валидность
     */
    boolean isInnValid(String inputInn, String inn);

    /**
     *Метод проверяет что банковский счет еще активен
     * @param expiredDate дата окончания поддержки счета
     * @param inputExpiredDate входная дата окончания поддержки счета
     * @return валидность
     */
    boolean isBankAccountExpired(LocalDate inputExpiredDate, LocalDate expiredDate);
}
