package security.service;

import com.data.exceptions.UserNotFoundException;
import com.data.dto.security.auth.AuthCredentialInfo;
import com.data.dto.security.auth.AuthUserToken;
import com.data.model.security.user.User;
import com.data.model.security.user.UserLogon;
import com.data.repo.UserLogonRepository;
import com.data.repo.UserRepository;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import security.component.JwtTokenProvider;
import security.exception.InvalidCredentialException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;
    private final UserLogonRepository userLogonRepository;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 UserRepository userRepository,
                                 UserLogonRepository userLogonRepository,
                                 JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.userLogonRepository = userLogonRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public AuthUserToken authentication(AuthCredentialInfo authCredentialInfo) {
        try {
            String login = authCredentialInfo.getLogin();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login, authCredentialInfo.getPassword()));
            User user = userRepository.findByUserLogin(login).orElseThrow(UserNotFoundException::new);
            UserLogon userLogon = userLogonRepository.findByUserAndUserLogin(user, login).orElseThrow(UserNotFoundException::new);
            String token = jwtTokenProvider.createToken(user.getUserLogin(), userLogon.getRoleType().name());
           log.info("token: " + token);
            return AuthUserToken.builder()
                    .token(token)
                    .userId(user.getUserId())
                    .roleType(userLogon.getRoleType())
                    .login(login)
                    .build();

        } catch (AuthenticationException e) {
            e.printStackTrace();
            throw new InvalidCredentialException();
        }
    }
}
