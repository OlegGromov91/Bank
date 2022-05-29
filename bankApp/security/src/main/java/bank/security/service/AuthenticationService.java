package bank.security.service;

import bank.data.exceptions.UserNotFoundException;
import bank.data.dto.security.auth.AuthCredentialInfo;
import bank.data.dto.security.auth.AuthUserToken;
import bank.data.model.security.user.User;
import bank.data.model.security.user.UserLogon;
import bank.data.repo.UserLogonRepository;
import bank.data.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import bank.security.component.JwtTokenProvider;
import bank.security.exception.InvalidCredentialException;
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
