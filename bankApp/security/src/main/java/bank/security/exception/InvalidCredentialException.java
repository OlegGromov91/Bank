package bank.security.exception;

public class InvalidCredentialException extends RuntimeException {

    private static final String DEFAULT_MESSAGE = "invalid.login.or.password";

    public InvalidCredentialException() {
        super(DEFAULT_MESSAGE);
    }

    public InvalidCredentialException(String message) {
        super(message);
    }

    public InvalidCredentialException(String message, Throwable cause) {
        super(message, cause);
    }
}
