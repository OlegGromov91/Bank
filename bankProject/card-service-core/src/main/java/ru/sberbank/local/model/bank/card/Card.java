package ru.sberbank.local.model.bank.card;

import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import ru.sberbank.local.model.security.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "BANK_CARD")
public class Card {
    public static final String USER_PROPERTY = "user";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cardId;

    @Max(value = 16L)
    @Column(name = "card_number", nullable = false, length = 16)
    private String cardNumber;

    @Column(name = "pin_code", nullable = false)
    private String pinCodeHash;

    @Column(name = "cvv", nullable = false)
    private String cvvHash;

    @Column(name = "expired_date", nullable = false)
    private LocalDate expiredDate;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    @Column(name = "card_is_active", columnDefinition="boolean default true")
    private boolean isActive;

    @Column(name = "card_is_block", columnDefinition="boolean default false")
    private boolean isBlock;

    @Enumerated(EnumType.STRING)
    @Column(name = "card_type")
    private CardType cardType;

    @Enumerated(EnumType.STRING)
    @Column(name = "currency_type")
    private CurrencyType currencyType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_system")
    private PaymentSystem paymentSystem;

    @Column(name = "money_amount", columnDefinition="Decimal(10,2) default '0.00'", nullable = false)
    private BigDecimal moneyAmount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIdentityInfo(generator= ObjectIdGenerators.PropertyGenerator.class, property="userId")
    @JsonIdentityReference(alwaysAsId=true)
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return Objects.equals(cardId, card.cardId) &&
                Objects.equals(cardNumber, card.cardNumber) &&
                Objects.equals(pinCodeHash, card.pinCodeHash) &&
                Objects.equals(cvvHash, card.cvvHash) &&
                Objects.equals(expiredDate, card.expiredDate) &&
                Objects.equals(createdDate, card.createdDate) &&
                cardType == card.cardType &&
                paymentSystem == card.paymentSystem;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, cardNumber, pinCodeHash, cvvHash, expiredDate, createdDate, cardType, paymentSystem);
    }
}
