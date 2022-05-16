package ru.sberbank.local.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import ru.sberbank.local.comons.exceptions.InvalidCredentialException;
import ru.sberbank.local.comons.exceptions.UserNotFoundException;
import ru.sberbank.local.model.security.user.AuthCredentialInfo;
import ru.sberbank.local.model.security.user.AuthUserToken;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.model.security.user.UserLogon;
import ru.sberbank.local.repo.UserLogonRepository;
import ru.sberbank.local.repo.UserRepository;
import ru.sberbank.local.security.component.JwtTokenProvider;

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
            System.out.println(token);
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
