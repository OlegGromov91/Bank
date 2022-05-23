package com.validation.cardValidator;

import com.data.model.bank.card.Card;
import com.validation.Validator;
import com.validation.exceptions.CardIsBlockException;
import com.validation.exceptions.CardPinIsInvalidBlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import security.component.PasswordEncoder;

@Slf4j
@Component
public class CardSecurityValidatorComponent extends BaseCardValidator {


    private final PasswordEncoder passwordEncoder;

    public CardSecurityValidatorComponent(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public boolean pinIsValid(String inputPin, String cardPinCode) {
        if (codeIsValid(inputPin, Validator.MAX_PIN_CODE_LENGTH) & passwordEncoder.bCryptPasswordEncoder().matches(inputPin, cardPinCode)) {
            log.info("pin is valid");
            return true;
        }
        log.error(CardPinIsInvalidBlockException.DEFAULT_MESSAGE);
        throw new CardPinIsInvalidBlockException();
    }

    @Override
    public boolean CVVIsValid(String inputCVV, String cardCVV) {
        return passwordEncoder.bCryptPasswordEncoder().matches(inputCVV, cardCVV);
    }

    @Override
    public boolean isCardBlock(Card card) {
        if (card.isBlock()) {
            log.error("card.is.block id= " + card.getCardId());
            throw new CardIsBlockException("card.is.block id= " + card.getCardId() + " " + card.getCardNumber());
        }
        return false;
    }

}
