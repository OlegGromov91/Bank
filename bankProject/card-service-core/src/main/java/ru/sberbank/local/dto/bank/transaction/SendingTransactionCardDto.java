package ru.sberbank.local.dto.bank.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.local.component.cardComponent.BanksCommission;
import ru.sberbank.local.dto.bank.card.CardDto;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendingTransactionCardDto extends BasicTransactionDto {
    String cardRecipientNumber;
    String commissionBankType;
}
