package bank.core.contoller;

import bank.core.common.exceptions.UnlockRequestHasAlreadyBeenAcceptedException;
import com.data.exceptions.BankAccountNotFoundExistException;
import com.data.exceptions.CardNotFoundExistException;
import com.data.exceptions.UnlockRequestNotFoundException;
import com.data.exceptions.UserNotFoundException;
import com.frod.common.exceptions.FrodOperationException;
import com.validation.exceptions.CardIsBlockException;
import com.validation.exceptions.CardPinIsInvalidBlockException;
import com.validation.exceptions.IllegalOperationException;
import com.validation.exceptions.UserSecretWordIsNotValidException;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import security.exception.InvalidCredentialException;
import security.exception.JwtAuthenticationException;

/**
 * Контроллер отлова исключений
 */

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(FrodOperationException.class)
    @ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
    public ExceptionRestResponse frodError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.TOO_MANY_REQUESTS.value(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRestResponse internalServerError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
    }

    @ExceptionHandler(InvalidCredentialException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ExceptionRestResponse InvalidCredentialError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }

    @ExceptionHandler(JwtAuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ExceptionRestResponse jwtAuthenticationError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.UNAUTHORIZED.value(), exception.getMessage());
    }

    @ExceptionHandler(UnlockRequestHasAlreadyBeenAcceptedException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ExceptionRestResponse unlockRequestHasAlreadyBeenAcceptedError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.NOT_ACCEPTABLE.value(), exception.getMessage());
    }

    @ExceptionHandler(CardIsBlockException.class)
    @ResponseStatus(HttpStatus.LOCKED)
    public ExceptionRestResponse cardIsBlockError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.LOCKED.value(), exception.getMessage());
    }

    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRestResponse notEnoughMoneyError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(CardPinIsInvalidBlockException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionRestResponse cardPinIsInvalidError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    @ExceptionHandler(UserSecretWordIsNotValidException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ExceptionRestResponse userSecretWordIsNotValidError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.FORBIDDEN.value(), exception.getMessage());
    }

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRestResponse userIsDontExistError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(UnlockRequestNotFoundException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRestResponse unlockRequestNotFoundError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }


    @ExceptionHandler(BankAccountNotFoundExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRestResponse bankAccountIsDontExistError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @ExceptionHandler(CardNotFoundExistException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ExceptionRestResponse cardIsDontExistError(Exception exception) {
        exception.printStackTrace();
        return new ExceptionRestResponse(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
    }

    @Value
    private class ExceptionRestResponse {
        int code;
        String message;
    }
}