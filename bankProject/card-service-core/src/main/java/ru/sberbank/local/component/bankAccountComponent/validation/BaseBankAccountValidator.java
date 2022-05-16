package ru.sberbank.local.component.bankAccountComponent.validation;

import java.time.LocalDate;

public class BaseBankAccountValidator implements BankAccountValidator {

    @Override
    public boolean isBeneficiaryAccountValid(String inputBeneficiaryAccount, String beneficiaryAccount) {
        return false;
    }

    @Override
    public boolean isBikValid(String inputBik, String bik) {
        return false;
    }

    @Override
    public boolean isInnValid(String inputInn, String inn) {
        return false;
    }

    @Override
    public boolean isBankAccountExpired(LocalDate inputExpiredDate, LocalDate expiredDate) {
        return false;
    }


}
