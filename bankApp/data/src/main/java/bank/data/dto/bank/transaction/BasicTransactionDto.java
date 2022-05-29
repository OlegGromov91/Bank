package bank.data.dto.bank.transaction;

import bank.data.model.bank.card.Card;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BasicTransactionDto {
        Card card;
        String money;
        String cardNumber;
}
