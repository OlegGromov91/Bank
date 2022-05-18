package security.model;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

public enum RoleType {

    ADMIN(Set.of(Permission.USER_WRITE,
            Permission.USER_READ,
            Permission.TRANSACTION_BLOCK_DELETE,
            Permission.HISTORY_READ,
            Permission.HISTORY_WRITE,
            Permission.CARD_WRITE,
            Permission.BANK_ACCOUNT_WRITE,
            Permission.BANK_ACCOUNT_READ
    )),
    OPERATOR(Set.of(Permission.USER_READ,
            Permission.TRANSACTION_BLOCK_DELETE,
            Permission.HISTORY_READ,
            Permission.HISTORY_WRITE,
            Permission.CARD_READ,
            Permission.CARD_UNLOCK,
            Permission.BANK_ACCOUNT_WRITE,
            Permission.BANK_ACCOUNT_READ
    )),

    USER(Set.of(Permission.USER_READ,
            Permission.USER_WRITE,
            Permission.HISTORY_WRITE,
            Permission.CARD_WRITE,
            Permission.CARD_CREATE,
            Permission.CARD_READ,
            Permission.HISTORY_READ,
            Permission.TRANSACTION_WRITE,
            Permission.BANK_ACCOUNT_WRITE,
            Permission.BANK_ACCOUNT_READ,
            Permission.BANK_ACCOUNT__CREATE
    ));

    private final Set<Permission> permissions;

    RoleType(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        return getPermissions().stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission())).collect(Collectors.toSet());
    }
}
