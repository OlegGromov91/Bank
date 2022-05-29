package bank.validation.exceptions;

public class UserSecretWordIsNotValidException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "secret.code.is.not.valid";

    public UserSecretWordIsNotValidException() {
        super(DEFAULT_MESSAGE);
    }

    public UserSecretWordIsNotValidException(String message) {
        super(message);
    }

    public UserSecretWordIsNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
