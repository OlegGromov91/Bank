package bank.core.component.cardComponent;

import com.data.model.bank.card.CardType;
import com.data.model.bank.card.CurrencyType;
import com.data.model.bank.card.PaymentSystem;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Компонент создает номер карту
 */
@Component
public class CardNumberBuilderComponent {

    private static final int STEP_CARD_VALIDATOR = 10;
    private static final String SBER_BANK_IDENTIFY = "276160";
    private static final int MAX_ACCOUNT_NUMBER_LENGTH = 6;
    private static final String START_CHECK_SUM_VALUE = "0";
    private static final StringBuilder sb = new StringBuilder();

    /**
     * @param paymentSystem тип платежной системы
     * @param cardType      тип карты
     * @param currencyType  валюта
     *                      метод генерирует номер карты
     * @return номер карты
     */
    public String buildCardNumber(PaymentSystem paymentSystem, CardType cardType, CurrencyType currencyType) {
        this.sb.delete(0, Integer.MAX_VALUE);
        sb.append(paymentSystem.systemIdentify)
                .append(SBER_BANK_IDENTIFY)
                .append(cardType.typeIdentify)
                .append(currencyType.currencyCode)
                .append(createAccountNumber())
                .append(generateCheckSum(sb.toString()));
        return sb.toString();
    }

    /**
     * @return возвращаем счет номера карты, который указывается на номере с 10 по 15 знак
     */
    private String createAccountNumber() {
        return new SecureRandom()
                .ints(MAX_ACCOUNT_NUMBER_LENGTH, 1, 9)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }

    /**
     * @param value 15-значный номер сгенерированный ранее
     *              cardNumberSum (двухзначное число) - содержит сумму всех знаков номера карты полученного при помощи алгоритма Луна.
     *              Затем берется последний знак суммы и вычитается из числа 10, на который без остатка должен делиться 16-значный номер карты.
     *              Тем самым вычисляем число, сложив которое с cardNumberSum и пропустив через валидацию Луна, получим валидный номер.
     *              Если число изначально валидно, то возвращаем стартовое значение чек суммы = 0, если нет возвращаем нехватающий остаток checkSum.
     * @return
     */
    private String generateCheckSum(String value) {
        String cardNumberSum = String.valueOf(generateSumFromCardNumber(value));
        int checkSum = STEP_CARD_VALIDATOR - Integer.parseInt(cardNumberSum.substring(1));
        return validate(cardNumberSum) | checkSum == STEP_CARD_VALIDATOR ?
                this.START_CHECK_SUM_VALUE :
                String.valueOf(checkSum);
    }

    private boolean validate(String value) {
        return generateSumFromCardNumber(value) % STEP_CARD_VALIDATOR == 0;
    }

    private int generateSumFromCardNumber(String value) {
        int[] numbers = Arrays.stream(value.replaceAll(" ", "").split("")).map(Integer::valueOf).mapToInt(el -> el).toArray();

        for (int i = numbers.length - 2; i >= 0; i -= 2) {
            numbers[i] = numbers[i] * 2;
            if (numbers[i] > 9) {
                numbers[i] = numbers[i] - 9;
            }
        }
        return Arrays.stream(numbers).sum();
    }
}
