package security.model;

import com.data.model.security.user.User;
import com.data.model.security.user.UserLogon;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
public class SecurityUser implements UserDetails {

    private final String userName;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;
    private final boolean isActive;


    public SecurityUser(String userName, String password, List<SimpleGrantedAuthority> authorities, boolean isActive) {
        this.userName = userName;
        this.password = password;
        this.authorities = authorities;
        this.isActive = isActive;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive;
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }

    public static UserDetails fromUser(User user, UserLogon userLogon) {

        String userRole = Arrays.stream(RoleType.values())
                .map(Enum::toString)
                .filter(role -> role.equals(userLogon.getRoleType().toString())).findFirst().orElseThrow();

        return new org.springframework.security.core.userdetails.User(
                userLogon.getUserLogin(),
                userLogon.getPasswordHash(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                user.isActive(),
                RoleType.valueOf(userRole).getAuthorities()
        );
    }
}
