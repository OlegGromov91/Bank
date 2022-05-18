package com.data.exceptions;

public class BankAccountNotFoundExistException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "bank.account.don't.exist";

    public BankAccountNotFoundExistException() {
        super(DEFAULT_MESSAGE);
    }

    public BankAccountNotFoundExistException(String message) {
        super(message);
    }

    public BankAccountNotFoundExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
