package ru.sberbank.local.comons.exceptions;

public class UserNotFoundException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "user.don't.exist";

    public UserNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
