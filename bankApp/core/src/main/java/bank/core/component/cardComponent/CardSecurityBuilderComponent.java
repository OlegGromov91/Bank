package bank.core.component.cardComponent;


import bank.validation.cardValidator.CardSecurityValidatorComponent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import bank.security.component.PasswordEncoder;

import java.security.SecureRandom;
import java.util.stream.Collectors;

/**
 * Компонент кодирует ответвственные данные для карты
 */
@Component
public class CardSecurityBuilderComponent {

    private final PasswordEncoder passwordEncoder;
    private final CardSecurityValidatorComponent cardSecurityValidatorComponent;

    @Autowired
    public CardSecurityBuilderComponent(PasswordEncoder passwordEncoder,
                                        CardSecurityValidatorComponent cardSecurityValidatorComponent) {
        this.passwordEncoder = passwordEncoder;
        this.cardSecurityValidatorComponent = cardSecurityValidatorComponent;
    }

    /**
     * Метод кодирует CVV
     *
     * @return cvv hash
     */
    public String encryptCVV() {
        return passwordEncoder.bCryptPasswordEncoder().encode(buildCVV());
    }

    /**
     * Метод кодирует пин код
     *
     * @param pin в сыром виде
     * @return пин код hash
     */
    public String encryptPin(String pin) {
        if (!cardSecurityValidatorComponent.codeIsValid(pin, cardSecurityValidatorComponent.MAX_PIN_CODE_LENGTH)) {
            throw new IllegalArgumentException("pin.code.is.not.valid");
        }
        return passwordEncoder.bCryptPasswordEncoder().encode(pin);
    }

    /**
     * Метод строит cvv с помощью рандома
     *
     * @return Cvv в сыром виде
     */
    private String buildCVV() {
        return new SecureRandom()
                .ints(cardSecurityValidatorComponent.MAX_CVV_CODE_LENGTH, 0, 6)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining());
    }


}
