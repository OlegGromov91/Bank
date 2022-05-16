package ru.sberbank.local.dto.bank.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.local.model.bank.bankAccount.BankAccount;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingTransactionAccountDto extends BasicTransactionDto{

    private BankAccount beneficiaryBankAccountFrom;
    private BankAccount beneficiaryBankAccountTo;
    private String beneficiaryAccount;

}
