package com.validation.userValidation;

import com.validation.Validator;

/**
 * базовая валидация для пользователя
 */
public interface BaseUserValidation extends Validator {

    /**
     * Метод проверяет секретное слово пользователя. Используется при отправки заявки на разблокировку карты
     * @param secretWord секретное слово в сыром виде
     * @param secretWordHash hash секретного слова
     * @return валидность
     */
    boolean isUserSecretWordValid(String secretWord, String secretWordHash);

}
