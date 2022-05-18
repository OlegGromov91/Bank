package com.data.dto.bank.transaction;

import com.data.model.bank.bankAccount.BankAccount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingTransactionAccountDto extends BasicTransactionDto{

    private BankAccount beneficiaryBankAccountFrom;
    private BankAccount beneficiaryBankAccountTo;
    private String beneficiaryAccount;

}
