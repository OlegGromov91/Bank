package bank.core.common.exceptions;

import lombok.extern.slf4j.Slf4j;

public class UnlockRequestHasAlreadyBeenAcceptedException extends RuntimeException {

    public static final String DEFAULT_MESSAGE = "unlock.request.has.already.been.accepted";

    public UnlockRequestHasAlreadyBeenAcceptedException() {
        super(DEFAULT_MESSAGE);
    }

    public UnlockRequestHasAlreadyBeenAcceptedException(String message) {
        super(message);
    }

    public UnlockRequestHasAlreadyBeenAcceptedException(String message, Throwable cause) {
        super(message, cause);
    }


}
