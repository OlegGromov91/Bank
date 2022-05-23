package com.validation.exceptions;

public class CardPinIsInvalidBlockException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "pin.code.is.invalid";

    public CardPinIsInvalidBlockException() {
        super(DEFAULT_MESSAGE);
    }

    public CardPinIsInvalidBlockException(String message) {
        super(message);
    }

    public CardPinIsInvalidBlockException(String message, Throwable cause) {
        super(message, cause);
    }
}
