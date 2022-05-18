package security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

@Getter
public class UserHaveIllegalRoleException extends AuthenticationException {

    private HttpStatus httpStatus;
    public static final String DEFAULT_MESSAGE = "user.have.illegal.role";

    public UserHaveIllegalRoleException(String msg, Throwable cause) {
        super(DEFAULT_MESSAGE + msg, cause);
    }

    public UserHaveIllegalRoleException(String msg, HttpStatus httpStatus) {
        super(DEFAULT_MESSAGE + msg);
        this.httpStatus = httpStatus;
    }
}
