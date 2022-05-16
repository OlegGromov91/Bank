package ru.sberbank.local.dto.bank.transaction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.sberbank.local.dto.bank.card.CardDto;
import ru.sberbank.local.model.bank.card.Card;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicTransactionDto {
        Card card;
        String money;
        String cardNumber;
}
