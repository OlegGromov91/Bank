package com.validation.exceptions;

public class CardPinIsInvalidBlockException extends RuntimeException {

    public CardPinIsInvalidBlockException() {
        super();
    }

    public CardPinIsInvalidBlockException(String message) {
        super(message);
    }

    public CardPinIsInvalidBlockException(String message, Throwable cause) {
        super(message, cause);
    }
}
