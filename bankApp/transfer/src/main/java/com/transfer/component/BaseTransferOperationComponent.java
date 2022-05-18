package com.transfer.component;

import com.data.model.bank.BanksCommission;
import com.data.model.bank.bankAccount.BankAccount;
import com.data.model.bank.card.Card;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Компонент для операций с картами при транзакции
 */

@Component
public class BaseTransferOperationComponent {

    /**
     * Метод пополняет баланс карты
     * @param card карта
     * @param money сумма пополнения
     * @return карту с пополненым балансом
     */
    public Card increaseMoneyTo(Card card, String money) {
        card.setMoneyAmount(card.getMoneyAmount().add(new BigDecimal(money)));
        return card;
    }

    /**
     * Метод снимает деньги с карты. Используется во время транзакций внутри банка
     * @param card карта
     * @param money сумма снятия
     * @return карту
     */
    public Card decreaseMoneyFrom(Card card, String money) {
        card.setMoneyAmount(card.getMoneyAmount().subtract(new BigDecimal(money)));
        return card;
    }

    /**
     * Метод снимает деньги с карты. Используется во время транзакций в другие банки
     * @param card карта
     * @param money сумма снятия
     * @param commissionBankType комиссия другого банка
     * @return карту
     */
    public Card decreaseMoneyFromWithCommission(Card card, String money, String commissionBankType) {
        card.setMoneyAmount(card.getMoneyAmount().subtract(new BigDecimal(money)));
        card.setMoneyAmount(card.getMoneyAmount().subtract(BanksCommission.valueOf(commissionBankType).commissionAmount));
        return card;
    }

    /**
     * Метод пополняет баланс карты
     * @param bankAccount счет
     * @param money сумма пополнения
     * @return счет с пополненым балансом
     */
    public BankAccount increaseMoneyTo(BankAccount bankAccount, String money) {
        bankAccount.setMoneyAmount(bankAccount.getMoneyAmount().add(new BigDecimal(money)));
        return bankAccount;
    }

    /**
     * Метод снимает деньги с карты. Используется во время транзакций внутри банка
     * @param bankAccount счет
     * @param money сумма снятия
     * @return счет
     */
    public BankAccount decreaseMoneyFrom(BankAccount bankAccount, String money) {
        bankAccount.setMoneyAmount(bankAccount.getMoneyAmount().subtract(new BigDecimal(money)));
        return bankAccount;
    }

}
