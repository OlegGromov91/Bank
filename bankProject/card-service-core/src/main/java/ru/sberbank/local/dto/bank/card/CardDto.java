package ru.sberbank.local.dto.bank.card;

import lombok.*;

import ru.sberbank.local.dto.security.user.UserDto;
import ru.sberbank.local.model.bank.card.CardType;
import ru.sberbank.local.model.bank.card.CurrencyType;
import ru.sberbank.local.model.bank.card.PaymentSystem;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CardDto {

    private Long cardId;
    private String cardNumber;
    private String pinCode;
    private String cvv;
    private LocalDate expiredDate;
    private LocalDate createdDate;
    private Boolean isActive;
    private Boolean isBlock;
    private CardType cardType;
    private CurrencyType currencyType;
    private PaymentSystem paymentSystem;
    private BigDecimal moneyAmount;
    private Long userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardDto card = (CardDto) o;
        return Objects.equals(cardId, card.cardId) &&
                Objects.equals(cardNumber, card.cardNumber) &&
                Objects.equals(pinCode, card.pinCode) &&
                Objects.equals(cvv, card.cvv) &&
                Objects.equals(expiredDate, card.expiredDate) &&
                Objects.equals(createdDate, card.createdDate) &&
                cardType == card.cardType &&
                paymentSystem == card.paymentSystem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, cardNumber, pinCode, cvv, expiredDate, createdDate, cardType, paymentSystem);
    }
}
