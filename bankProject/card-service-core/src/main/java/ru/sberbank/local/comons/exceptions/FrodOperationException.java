package ru.sberbank.local.comons.exceptions;


public class FrodOperationException extends RuntimeException {

    public FrodOperationException() {
        super();
    }

    public FrodOperationException(String message) {
        super(message);
    }

    public FrodOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}
