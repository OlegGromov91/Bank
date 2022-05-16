package ru.sberbank.local.dto.bank.bankAccount;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.local.model.bank.bankAccount.CorrespondentBankType;
import ru.sberbank.local.model.bank.card.CurrencyType;

import java.math.BigDecimal;
import java.time.LocalDate;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountDto {

    private Long bankAccountId;
    private String beneficiaryAccount;
    private String bik;
    private CorrespondentBankType correspondentBankType;
    private String inn;
    private Long beneficiaryId;
    private LocalDate expiredDate;
    private LocalDate createdDate;
    private boolean active;
    private BigDecimal moneyAmount;
    private CurrencyType currencyType;
}
