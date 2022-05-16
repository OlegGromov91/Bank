package ru.sberbank.local.comons.exceptions;

public class CardIsBlockException extends RuntimeException {

    public CardIsBlockException() {
        super();
    }

    public CardIsBlockException(String message) {
        super(message);
    }

    public CardIsBlockException(String message, Throwable cause) {
        super(message, cause);
    }
}
