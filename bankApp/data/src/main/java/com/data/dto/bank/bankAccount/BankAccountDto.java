package com.data.dto.bank.bankAccount;

import com.data.model.bank.bankAccount.CorrespondentBankType;
import com.data.model.bank.card.CurrencyType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
