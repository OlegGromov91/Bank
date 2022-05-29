package bank.security.model;

import lombok.Getter;

@Getter
public enum Permission {

    USER_READ("user:read"),
    USER_WRITE("user:write"),
    CARD_READ("card:read"),
    CARD_WRITE("card:write"),
    CARD_UNLOCK("card:unlock"),
    CARD_CREATE("card:create"),
    TRANSACTION_WRITE("transaction:write"),
    TRANSACTION_BLOCK_RESTART("transaction:restart"),
    TRANSACTION_BLOCK_DELETE("transaction:delete"),
    HISTORY_READ("history:read"),
    HISTORY_WRITE("history:write"),
    BANK_ACCOUNT_WRITE("bankAccount:write"),
    BANK_ACCOUNT_READ("bankAccount:read"),
    BANK_ACCOUNT__CREATE("bankAccount:create");


    private final String permission;

     Permission(String permission) {
        this.permission = permission;
    }

}
