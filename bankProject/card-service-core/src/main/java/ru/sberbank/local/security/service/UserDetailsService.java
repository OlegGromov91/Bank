package ru.sberbank.local.security.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.sberbank.local.comons.exceptions.UserNotFoundException;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.model.security.user.UserLogon;
import ru.sberbank.local.repo.UserLogonRepository;
import ru.sberbank.local.repo.UserRepository;
import ru.sberbank.local.security.model.SecurityUser;

@Service("appUserDetailsService")
public class UserDetailsService  implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final UserLogonRepository userLogonRepository;


    public UserDetailsService(UserRepository userRepository, UserLogonRepository userLogonRepository) {
        this.userRepository = userRepository;
        this.userLogonRepository = userLogonRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userLogin) {
        User user = userRepository.findByUserLogin(userLogin).orElseThrow(UserNotFoundException::new);
        UserLogon userLogon = userLogonRepository.findByUserAndUserLogin(user, userLogin).orElseThrow(UserNotFoundException::new);
        return SecurityUser.fromUser(user, userLogon);
    }
}
