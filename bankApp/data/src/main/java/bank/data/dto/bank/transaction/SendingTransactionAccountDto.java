package bank.data.dto.bank.transaction;

import bank.data.model.bank.bankAccount.BankAccount;
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
