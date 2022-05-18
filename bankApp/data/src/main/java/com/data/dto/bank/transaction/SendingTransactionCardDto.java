package com.data.dto.bank.transaction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingTransactionCardDto extends BasicTransactionDto {
    String cardRecipientNumber;
    String commissionBankType;
}
