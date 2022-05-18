package com.frod.common.exceptions;


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
