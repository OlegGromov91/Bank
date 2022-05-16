package ru.sberbank.local.service;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.sberbank.local.comons.exceptions.UserNotFoundException;
import ru.sberbank.local.dto.security.user.UserDto;
import ru.sberbank.local.mapper.UserMapper;
import ru.sberbank.local.model.security.user.User;
import ru.sberbank.local.repo.UserRepository;


@Service
public class UserService {

    UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    @PreAuthorize("hasAuthority('user:read')")
    public UserDto getUser(Long userId){
        User user = userRepository.findById(userId).orElseThrow(UserNotFoundException::new);
        return UserMapper.INSTANCE.toDto(user);
    }

}
