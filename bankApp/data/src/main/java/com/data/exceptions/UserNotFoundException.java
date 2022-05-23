package com.data.exceptions;

import lombok.Getter;

public class UserNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "user.don't.exist";

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
