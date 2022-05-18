package bank.core.component.cardComponent;

import com.data.model.bank.card.Card;
import com.data.model.bank.card.CardType;
import com.data.model.bank.card.CurrencyType;
import com.data.model.bank.card.PaymentSystem;
import com.data.model.security.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Компонент создает новую карту
 */

@Component
public class CardBuilderComponent {

    private final CardNumberBuilderComponent cardNumberBuilderComponent;
    private final CardSecurityBuilderComponent cardSecurityBuilderComponent;

    @Autowired
    public CardBuilderComponent(CardNumberBuilderComponent cardNumberBuilderComponent,
                                CardSecurityBuilderComponent cardSecurityBuilderComponent) {
        this.cardNumberBuilderComponent = cardNumberBuilderComponent;
        this.cardSecurityBuilderComponent = cardSecurityBuilderComponent;
    }

    /**
     * Метод создает новую карту, путем построения номера в отдельном компоненте @see CardNumberBuilderComponent
     * cvv задается в @see CardSecurityBuilderComponent рандомно
     * Дата окончания поддержки карты задается путем прибавления dateExpiredSum,
     * которое записано в типе карты @see cardType.
     *
     * @param user         владелец карты
     * @param pinCode      пин код в сыром виде
     * @param cardType     тип карты
     * @param currencyType тип валюты
     * @param paySystem    тип платежной системы
     * @return Возвращаем новую карту
     */
    public Card buildNewCard(User user, String pinCode, String cardType, String currencyType, String paySystem) {

        String cardNumber = cardNumberBuilderComponent.buildCardNumber(
                PaymentSystem.valueOf(paySystem),
                CardType.valueOf(cardType),
                CurrencyType.valueOf(currencyType));

        LocalDate dateCreating = LocalDate.now();

        return Card.builder()
                .cardNumber(cardNumber)
                .pinCodeHash(cardSecurityBuilderComponent.encryptPin(pinCode))
                .cvvHash(cardSecurityBuilderComponent.encryptCVV())
                .createdDate(dateCreating)
                .expiredDate(dateCreating.plusYears(CardType.valueOf(cardType).dateExpiredSum))
                .cardType(CardType.valueOf(cardType))
                .currencyType(CurrencyType.valueOf(currencyType))
                .paymentSystem(PaymentSystem.valueOf(paySystem))
                .moneyAmount(new BigDecimal("0.00"))
                .user(user)
                .build();
    }


}
