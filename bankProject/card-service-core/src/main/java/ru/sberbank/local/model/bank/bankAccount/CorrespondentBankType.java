package ru.sberbank.local.model.bank.bankAccount;

public enum CorrespondentBankType {

    PAO_SBERBANK_POVOLZHEY("ПАО Сбербанк Поволжье", 7L);

    private final String correspondentBank;
    private final Long expiredDateDuration;

    CorrespondentBankType(String correspondentBank, Long expiredDateDuration) {
        this.correspondentBank = correspondentBank;
        this.expiredDateDuration = expiredDateDuration;
    }

    public String getCorrespondentBank() {
        return correspondentBank;
    }

    public Long getExpiredDateDuration() {
        return expiredDateDuration;
    }
}
