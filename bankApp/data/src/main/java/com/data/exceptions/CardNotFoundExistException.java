package com.data.exceptions;

public class CardNotFoundExistException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "card.don't.exist";

    public CardNotFoundExistException() {
        super(DEFAULT_MESSAGE);
    }

    public CardNotFoundExistException(String message) {
        super(message);
    }

    public CardNotFoundExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
