package bank.core.component.cardComponent;

import com.data.model.bank.card.Card;
import com.data.model.bank.card.CurrencyType;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * Компонент для конвертации валют
 * PS не доделан
 * PSS использовать Jsoup
 */
@Component
public class CurrencyConverter {

    private Map<List<CurrencyType>, BigDecimal> bankConvertation = Map.of(
            List.of(CurrencyType.USD, CurrencyType.EUR), new BigDecimal("0.95")
    );

    public BigDecimal automaticCurrencyConvertation(Card from, Card to, BigDecimal money) {
        if (to.getCurrencyType() == from.getCurrencyType()) {
            return money;
        }

        return null;
    }


    private BigDecimal convertOneRurToOneUsd() {
        return new BigDecimal("1").divide(CurrencyType.USD.currencyCurse, RoundingMode.CEILING);
    }

    private BigDecimal convertOneRurToOneEur() {
        return new BigDecimal("1").divide(CurrencyType.EUR.currencyCurse, RoundingMode.CEILING);
    }


}
