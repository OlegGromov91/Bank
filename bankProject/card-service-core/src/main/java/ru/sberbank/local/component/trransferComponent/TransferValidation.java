package ru.sberbank.local.component.trransferComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sberbank.local.comons.exceptions.IllegalOperationException;
import ru.sberbank.local.component.cardComponent.BanksCommission;
import ru.sberbank.local.component.cardComponent.validation.CardSecurityValidatorComponent;
import ru.sberbank.local.component.cardComponent.validation.CardValidator;
import ru.sberbank.local.model.bank.card.Card;
import ru.sberbank.local.model.bank.history.OperationType;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.service.FrodService;

import java.util.Objects;

/**
 * Компонент для валидации транзакций
 */
@Component
public class TransferValidation {

    private final CardSecurityValidatorComponent cardSecurityValidatorComponent;
    private final CardValidator cardValidator;
    private final FrodService frodService;

    @Autowired
    public TransferValidation(CardSecurityValidatorComponent cardSecurityValidatorComponent,
                              CardValidator cardValidator,
                              FrodService frodService) {
        this.cardSecurityValidatorComponent = cardSecurityValidatorComponent;
        this.cardValidator = cardValidator;
        this.frodService = frodService;
    }

    /**
     * Метод проверяет что карта не заблокирована и на ней есть деньги. Используется во время транзакций внутри банка
     * @param card карта
     * @param money сумма снятия
     * @return валидность
     */
    public boolean baseValidationForTransfer(Card card, String money) {
        return cardSecurityValidatorComponent.isCardBlock(card) &
                cardValidator.isMoneyEnough(money, card.getMoneyAmount());
    }

    /**
     * Метод проверяет что карта принадлежит владельцу. Используется во время транзакций при перевода с карты на карту клиента
     * @param card карта
     * @param user владелец карты
     * @return валидность
     */
    public boolean baseValidationForUserSelfTransfer(Card card, User user) {
        if (Objects.equals(card.getUser().getUserId(), user.getUserId())) {
            return true;
        }
        throw new IllegalOperationException("user.is.not.card.holder");
    }

    /**
     * Метод проверяет что карта не заблокирована, что на ней есть деньги сумма перевода + комиссия банка. Так же идет проверка на фрод
     *  Используется во время транзакций в другой банк
     * @param cardSender владелец карты
     * @param cardRecipientNumber номер карты получателя
     * @param money сумма
     * @param commissionBankType тип банка получателя для списания комиссии
     * @param operationType тип операции нужен для сохранения в историю перевода если возникает фрод
     * @return валидность
     */
    public boolean baseValidationForTransferWithCommissionToOutsideBank(Card cardSender,
                                                                        String cardRecipientNumber,
                                                                        String money,
                                                                        String commissionBankType,
                                                                        OperationType operationType) {
        return cardSecurityValidatorComponent.isCardBlock(cardSender) &
                frodService.checkForAllFrod(cardSender, cardRecipientNumber, money, operationType) &
                cardValidator.isMoneyEnoughWithCommission(money, cardSender.getMoneyAmount(), BanksCommission.valueOf(commissionBankType).commissionAmount);
    }

}
