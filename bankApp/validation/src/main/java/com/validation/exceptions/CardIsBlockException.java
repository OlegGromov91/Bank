package com.validation.exceptions;

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
