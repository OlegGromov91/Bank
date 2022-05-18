package com.data.exceptions;


public class UnlockRequestNotFoundException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "unlock.request.not.found";

    public UnlockRequestNotFoundException() {
        super(DEFAULT_MESSAGE);
    }

    public UnlockRequestNotFoundException(String message) {
        super(message);
    }

    public UnlockRequestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }


}
