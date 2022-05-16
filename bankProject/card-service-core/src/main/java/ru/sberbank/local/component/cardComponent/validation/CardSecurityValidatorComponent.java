package ru.sberbank.local.component.cardComponent.validation;

import org.springframework.stereotype.Component;
import ru.sberbank.local.comons.exceptions.CardIsBlockException;
import ru.sberbank.local.comons.exceptions.CardPinIsInvalidBlockException;
import ru.sberbank.local.config.PasswordEncoder;
import ru.sberbank.local.model.bank.card.Card;

import java.util.Objects;

@Component
public class CardSecurityValidatorComponent extends BaseCardValidator {


    private final PasswordEncoder passwordEncoder;

    public CardSecurityValidatorComponent(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean pinIsValid(String inputPin, String cardPinCode) {
        if (codeIsValid(inputPin, MAX_PIN_CODE_LENGTH) & passwordEncoder.bCryptPasswordEncoder().matches(inputPin, cardPinCode)) {
            return true;
        }
        throw new CardPinIsInvalidBlockException("pin.code.is.invalid");
    }

    @Override
    public boolean CVVIsValid(String inputCVV, String cardCVV) {
        return passwordEncoder.bCryptPasswordEncoder().matches(inputCVV, cardCVV);
    }

    @Override
    public boolean isCardBlock(Card card) {
        if (card.isBlock()) {
            throw new CardIsBlockException("card.is.block " + card.getCardId() + " " + card.getCardNumber());
        }
        return false;
    }

}
