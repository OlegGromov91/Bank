package ru.sberbank.local.component.userComponent.validation;

import org.springframework.stereotype.Component;
import ru.sberbank.local.comons.exceptions.UserSecretWordIsNotValidException;
import ru.sberbank.local.config.PasswordEncoder;

/**
 * компонент валидации пользователя
 */
@Component
public class UserValidation implements BaseUserValidation {

    private final PasswordEncoder passwordEncoder;

    public UserValidation(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public boolean isUserSecretWordValid(String secretWord, String secretWordHash) {
        if(passwordEncoder.bCryptPasswordEncoder().matches(secretWord, secretWordHash)){
            return true;
        }
        throw new UserSecretWordIsNotValidException();
    }
}
