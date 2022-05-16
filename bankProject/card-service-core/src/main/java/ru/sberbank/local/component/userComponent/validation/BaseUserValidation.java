package ru.sberbank.local.component.userComponent.validation;

/**
 * базовая валидация для пользователя
 */
public interface BaseUserValidation {

    /**
     * Метод проверяет секретное слово пользователя. Используется при отправки заявки на разблокировку карты
     * @param secretWord секретное слово в сыром виде
     * @param secretWordHash hash секретного слова
     * @return валидность
     */
    boolean isUserSecretWordValid(String secretWord, String secretWordHash);

}
