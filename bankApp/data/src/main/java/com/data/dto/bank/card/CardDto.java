package com.data.dto.bank.card;

import com.data.model.bank.card.CurrencyType;
import com.data.model.bank.card.CardType;
import com.data.model.bank.card.PaymentSystem;
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

}
