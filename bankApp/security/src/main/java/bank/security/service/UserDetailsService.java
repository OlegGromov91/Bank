package bank.security.service;


import bank.data.model.security.user.User;
import bank.data.model.security.user.UserLogon;
import bank.data.exceptions.*;

import bank.data.repo.UserLogonRepository;
import bank.data.repo.UserRepository;
import bank.security.model.SecurityUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;


@Service("appUserDetailsService")
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;
    private final UserLogonRepository userLogonRepository;

    @Autowired
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
