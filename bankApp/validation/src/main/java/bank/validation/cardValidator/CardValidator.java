package bank.validation.cardValidator;

import bank.data.model.bank.card.Card;
import bank.validation.Validator;

import java.math.BigDecimal;

/**
 * Основная валидацция для карты
 */
public interface CardValidator extends Validator {


    /**
     * Метод проверяет достаточно ли денег для транзакций в другие банки.
     *
     * @param inputMoney  сумма для перевода
     * @param moneyOnCard сумма на карте
     * @param commission  сумма комиссии
     * @return валидность
     */
    boolean isMoneyEnoughWithCommission(String inputMoney, BigDecimal moneyOnCard, BigDecimal commission);

    /**
     * Метод проверяет правильно ли введен CVV.
     *
     * @param inputCVV cvv в сыром виде
     * @param cardCVV  cvv в виде hash Bcrypt
     * @return валидность
     */
    boolean CVVIsValid(String inputCVV, String cardCVV);

    /**
     * Метод проверяет правильно ли введен пин код.
     *
     * @param inputPin    пин код в сыром виде
     * @param cardPinCode пин код в виде hash Bcrypt
     * @return валидность
     */
    boolean pinIsValid(String inputPin, String cardPinCode);

    /**
     * Метод проверяет заблокирована ли карта.
     *
     * @param card карта, которую нужно проверить
     * @return валидность
     */
    boolean isCardBlock(Card card);
}
