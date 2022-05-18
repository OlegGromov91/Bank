package com.validation.userValidation;

import com.validation.exceptions.UserSecretWordIsNotValidException;
import org.springframework.stereotype.Component;
import security.component.PasswordEncoder;

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
        if (passwordEncoder.bCryptPasswordEncoder().matches(secretWord, secretWordHash)) {
            return true;
        }
        throw new UserSecretWordIsNotValidException();
    }
}
